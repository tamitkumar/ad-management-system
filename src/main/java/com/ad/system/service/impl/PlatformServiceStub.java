package com.ad.system.service.impl;

import com.ad.system.model.Campaign;
import com.ad.system.service.PlatformService;
import org.springframework.stereotype.Service;

@Service
public class PlatformServiceStub implements PlatformService {
    @Override
    public void startCampaign(Campaign campaign) {
        System.out.println("[STUB] Starting campaign" + campaign.getName() + " on platforms: " + campaign.getPlatforms());
    }

    @Override
    public void stopCampaign(Campaign campaign) {
        System.out.println("[STUB] Stopping campaign" + campaign.getName());
    }
}
