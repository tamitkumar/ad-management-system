package com.ad.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "ads", schema = "ad_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Long adId;

    @Column(name = "duration_seconds", nullable = false)
    private int durationSeconds;

    @Column(name = "scheduled_start", nullable = false)
    private Instant scheduledStart;

    @Column(name = "scheduled_end", nullable = false)
    private Instant scheduledEnd;
    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private CampaignEntity campaign;

    @ManyToMany
    @JoinTable(
            name = "ad_platforms",
            joinColumns = @JoinColumn(name = "ad_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id")
    )
    private Set<PlatformEntity> platforms;

    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL)
    private Set<AdStatusEntity> statuses;
}
