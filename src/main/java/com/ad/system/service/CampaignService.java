package com.ad.system.service;

import com.ad.system.model.Campaign;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CampaignService {
    Campaign createCampaign(Campaign campaign);
    List<Campaign> getActiveCampaigns();
    void activateCampaign(Campaign campaign);
    void deactivateCampaign(Campaign campaign);
    Page<Campaign> getAllCampaigns(Pageable pageable);
}
