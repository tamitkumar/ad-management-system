package com.ad.system.model;

import lombok.*;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignCommandMessage {
    private Long campaignId;
    private String platform; // YOUTUBE, FACEBOOK, etc.
    private String action;   // START or STOP
}
