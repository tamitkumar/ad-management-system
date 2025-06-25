package com.ad.system.repository;

import com.ad.system.entity.AdEntity;
import com.ad.system.entity.AdStatusEntity;
import com.ad.system.entity.AdStatusId;
import com.ad.system.entity.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdStatusRepository extends JpaRepository<AdStatusEntity, AdStatusId> {
    Optional<AdStatusEntity> findByAdAndPlatform(AdEntity ad, PlatformEntity platform);
}
