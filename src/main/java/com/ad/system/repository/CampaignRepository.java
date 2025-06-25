package com.ad.system.repository;

import com.ad.system.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    List<CampaignEntity> findByActiveTrue();
    List<CampaignEntity> findByStartTimeBeforeAndEndTimeAfterAndActiveFalse(Instant now1, Instant now2);
    List<CampaignEntity> findByEndTimeBeforeAndActiveTrue(Instant now);
    CampaignEntity findByCampaignId(Long campaignId);
    List<CampaignEntity> findAllByCampaignIdIn(List<Long> campaignId);
}
