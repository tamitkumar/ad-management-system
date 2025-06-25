package com.ad.system.model;

import lombok.*;

import java.util.Set;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Platform {
    private Integer platformId;
    private String name;
    private Set<Long> adIds;                // AdEntity.adId
    private Set<AdStatusTO> adStatuses;
}
