package com.ad.system.service;

import com.ad.system.model.Ad;

public interface SchedulerService {
    void scheduleAd(Ad ad);
    void stopAd(Long adId, String platformName);
}
