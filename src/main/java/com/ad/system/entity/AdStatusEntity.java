package com.ad.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "ad_status", schema = "ad_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AdStatusId.class)
public class AdStatusEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private AdEntity ad;

    @Id
    @ManyToOne
    @JoinColumn(name = "platform_id")
    private PlatformEntity platform;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AdStatus status; // ACTIVE, INACTIVE, STOPPED

    private Instant lastUpdated = Instant.now();
}
