package com.ad.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
    @JsonProperty("clientId")
    private Long clientId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("contactInfo")
    private String contactInfo;

    @JsonProperty("campaignIds")
    private List<Long> campaignIds;
}
