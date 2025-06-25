package com.ad.system.kafka;

import com.ad.system.model.Campaign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.kafka.core.KafkaTemplate;

@Component
@RequiredArgsConstructor
public class AdPlatformProducer {
    private final KafkaTemplate<String, Campaign> kafkaTemplate;

    public void sendStartCampaign(Campaign campaign) {
        kafkaTemplate.send("campaign-start", campaign);
    }

    public void sendStopCampaign(Campaign campaign) {
        kafkaTemplate.send("campaign-stop", campaign);
    }
}
