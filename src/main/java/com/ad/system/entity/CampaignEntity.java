package com.ad.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "campaigns", schema = "ad_system")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "campaign_id", nullable = false)
    private Long campaignId;

    @Column(nullable = false)
    private String name;
    @Column(name = "start_time")
    private Instant startTime;
    @Column(name = "end_time")
    private Instant endTime;
    private boolean active;
    @Column(name = "created_at")
    private Instant createdAt;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL)
    private Set<AdEntity> ads;
    @Version
    private Long version;

    @ElementCollection
    @CollectionTable(name = "campaigns_platforms", joinColumns = @JoinColumn(name = "campaign_entity_campaign_id"))
    @Column(name = "platforms")
    private Set<String> platforms; // YOUTUBE, FACEBOOK, etc.
}
