package com.ad.system.service.impl;

import com.ad.system.entity.AdEntity;
import com.ad.system.entity.CampaignEntity;
import com.ad.system.entity.ClientEntity;
import com.ad.system.exception.AdSystemException;
import com.ad.system.kafka.AdPlatformProducer;
import com.ad.system.model.Campaign;
import com.ad.system.repository.AdRepository;
import com.ad.system.repository.CampaignRepository;
import com.ad.system.repository.ClientRepository;
import com.ad.system.service.CampaignService;
import com.ad.system.utls.AdSystemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ad.system.utls.AdConstant.HTTP_CODE_500;

@Service
@RequiredArgsConstructor
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final ClientRepository clientRepository;
    private final AdRepository adRepository;
    private final AdPlatformProducer platformProducer;

    @Override
    public Campaign createCampaign(Campaign campaign) {
        campaign.setActive(false);
        ClientEntity client = clientRepository.findById(campaign.getClientId())
                .orElseThrow(() -> new AdSystemException(HTTP_CODE_500, "Client not found with id: " + campaign.getClientId()));
        Set<AdEntity> ads = new HashSet<>();
        if(CollectionUtils.isEmpty(campaign.getAdIds())) {
            ads = new HashSet<>(adRepository.findAllById(campaign.getAdIds()));
        }
        CampaignEntity entity = new CampaignEntity();
        entity.setName(campaign.getName());
        entity.setStartTime(campaign.getStartTime());
        entity.setEndTime(campaign.getEndTime());
        entity.setActive(campaign.isActive());
        entity.setPlatforms(campaign.getPlatforms());
        entity.setClient(client);
        entity.setAds(ads);
        CampaignEntity campaignDb =  campaignRepository.save(entity);
        return AdSystemUtils.toModel(campaignDb);
    }

    @Override
    public List<Campaign> getActiveCampaigns() {
        List<Campaign> campaign = new ArrayList<>();
        List<CampaignEntity> entity = campaignRepository.findByActiveTrue();
        entity.forEach(en -> campaign.add(AdSystemUtils.toModel(en)));
        return campaign;
    }

    @Override
    public void activateCampaign(Campaign campaign) {
        CampaignEntity entity = campaignRepository.findByCampaignId(campaign.getCampaignId());
        if(null != entity) {
            entity.setActive(true);
            CampaignEntity campaignEntity = campaignRepository.save(entity);
            campaign = AdSystemUtils.toModel(campaignEntity);
        }
        platformProducer.sendStartCampaign(campaign);
    }

    @Override
    public void deactivateCampaign(Campaign campaign) {
        CampaignEntity entity = campaignRepository.findByCampaignId(campaign.getCampaignId());
        entity.setActive(false);
        campaignRepository.save(entity);
        platformProducer.sendStopCampaign(campaign);
    }

//    @Override
//    @Cacheable("campaigns")
//    public List<Campaign> getAllCampaigns() {
//        List<Campaign> campaign = new ArrayList<>();
//        List<CampaignEntity> entity = campaignRepository.findAll();
//        entity.forEach(en -> campaign.add(AdSystemUtils.toModel(en)));
//        return campaign;
//    }

    @Override
    @Cacheable("campaigns")
    public Page<Campaign> getAllCampaigns(Pageable pageable) {
        Page<CampaignEntity> entityPage = campaignRepository.findAll(pageable);
        return entityPage.map(AdSystemUtils::toModel);
    }
}
