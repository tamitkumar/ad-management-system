package com.ad.system.model;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ad {
    private Long adId;
    private int durationSeconds;
    private Instant scheduledStart;
    private Instant scheduledEnd;
    private Instant createdAt;
    private Long campaignId;
    private Set<Integer> platformIds;       // PlatformEntity.platformId
    private Set<AdStatusTO> statuses;
}
