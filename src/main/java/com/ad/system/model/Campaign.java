package com.ad.system.model;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {
    private Long campaignId;
    private String name;
    private Instant startTime;
    private Instant endTime;
    private boolean active;
    private Long clientId;
    private List<Long> adIds;
    private Set<String> platforms;
}
