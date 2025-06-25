package com.ad.system.repository;

import com.ad.system.entity.PlatformEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformRepository extends JpaRepository<PlatformEntity, Integer> {
    Optional<PlatformEntity> findByName(String name);
}
