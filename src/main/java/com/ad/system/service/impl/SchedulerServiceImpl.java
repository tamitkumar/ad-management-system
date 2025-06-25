package com.ad.system.service.impl;

import com.ad.system.entity.AdEntity;
import com.ad.system.entity.AdStatus;
import com.ad.system.entity.AdStatusEntity;
import com.ad.system.entity.PlatformEntity;
import com.ad.system.model.Ad;
import com.ad.system.model.Campaign;
import com.ad.system.repository.AdRepository;
import com.ad.system.repository.AdStatusRepository;
import com.ad.system.repository.PlatformRepository;
import com.ad.system.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final KafkaTemplate<String, Campaign> kafkaTemplate;
    private final AdRepository adRepository;
    private final PlatformRepository platformRepository;
    private final AdStatusRepository adStatusRepository;

    @Override
    public void scheduleAd(Ad ad) {
        if (ad.getAdId() != null) {
            Optional<AdEntity> entity = adRepository.findById(ad.getAdId());
            entity.ifPresentOrElse(System.out::println, () -> adRepository.save(toEntity(ad)));
        }
    }

    @Override
    public void stopAd(Long adId, String platformName) {
        AdEntity ad = adRepository.findById(adId).orElseThrow(() -> new RuntimeException("Ad not found"));
        PlatformEntity platform = platformRepository.findByName(platformName)
                .orElseThrow(() -> new RuntimeException("Platform not found"));

        adStatusRepository.findByAdAndPlatform(ad, platform).ifPresent(status -> {
            status.setStatus(AdStatus.INACTIVE);
            adStatusRepository.save(status);
            kafkaTemplate.send("ad-events", Campaign.builder().campaignId(adId).name(platformName).active(false).build());
        });
    }

    private AdEntity toEntity(Ad ad) {
        // 1. Fetch platform entities
        Set<Integer> platformIds = ad.getPlatformIds();
        List<PlatformEntity> platforms = platformRepository.findAllById(platformIds);

        // 2. Create AdEntity
        AdEntity adEntity = new AdEntity();
        adEntity.setAdId(ad.getAdId());  // May be null for new entity
        adEntity.setDurationSeconds(ad.getDurationSeconds());
        adEntity.setScheduledStart(ad.getScheduledStart());
        adEntity.setScheduledEnd(ad.getScheduledEnd());
        adEntity.setCreatedAt(ad.getCreatedAt() != null ? ad.getCreatedAt() : Instant.now());
        adEntity.setPlatforms(new HashSet<>(platforms));

        // 3. Map statuses
        Set<AdStatusEntity> statusEntities = ad.getStatuses().stream()
                .map(statusTO -> {
                    AdStatusEntity statusEntity = new AdStatusEntity();
                    statusEntity.setAd(adEntity); // owning side
                    PlatformEntity platform = platforms.stream()
                            .filter(p -> p.getPlatformId().equals(statusTO.getPlatformId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Platform not found: " + statusTO.getPlatformId()));
                    statusEntity.setPlatform(platform);
                    statusEntity.setStatus(statusTO.getStatus());
                    statusEntity.setLastUpdated(statusTO.getLastUpdated() != null ? statusTO.getLastUpdated() : Instant.now());
                    return statusEntity;
                })
                .collect(Collectors.toSet());

        adEntity.setStatuses(statusEntities);

        return adEntity;
    }
}
