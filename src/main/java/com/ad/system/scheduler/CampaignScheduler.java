package com.ad.system.scheduler;

import com.ad.system.entity.CampaignEntity;
import com.ad.system.model.Campaign;
import com.ad.system.repository.CampaignRepository;
import com.ad.system.service.CampaignService;
import com.ad.system.utls.AdSystemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CampaignScheduler {
    private final CampaignRepository campaignRepository;
    private final CampaignService campaignService;

    @Scheduled(fixedRate = 60000) // every 1 minute
    public void checkAndActivateCampaigns() {
        Instant now = Instant.now();
        List<Campaign> startCampaign = new ArrayList<>();
        List<Campaign> stopCampaign = new ArrayList<>();
        List<CampaignEntity> toStart = campaignRepository.findByStartTimeBeforeAndEndTimeAfterAndActiveFalse(now, now);
        List<CampaignEntity> toStop = campaignRepository.findByEndTimeBeforeAndActiveTrue(now);
        toStart.forEach(en -> startCampaign.add(AdSystemUtils.toModel(en)));
        toStop.forEach(en -> stopCampaign.add(AdSystemUtils.toModel(en)));
        startCampaign.forEach(campaignService::activateCampaign);
        stopCampaign.forEach(campaignService::deactivateCampaign);
    }
}
