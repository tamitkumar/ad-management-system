package com.ad.system.model;

import com.ad.system.entity.AdStatus;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdStatusTO {
    private Long adId;
    private Integer platformId;
    private AdStatus status;
    private Instant lastUpdated;
}
