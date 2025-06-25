package com.ad.system.repository;

import com.ad.system.entity.AdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Long> {
}
