package com.ad.system.service;

import com.ad.system.model.Campaign;

public interface PlatformService {
    void startCampaign(Campaign campaign);
    void stopCampaign(Campaign campaign);
}
