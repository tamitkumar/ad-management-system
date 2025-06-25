package com.ad.system.utls;

import com.ad.system.entity.AdEntity;
import com.ad.system.entity.CampaignEntity;
import com.ad.system.entity.ClientEntity;
import com.ad.system.model.Campaign;
import com.ad.system.model.Client;

import java.util.List;
import java.util.stream.Collectors;

public interface AdSystemUtils {

    static Campaign toModel(CampaignEntity entity) {
        List<Long> adIds = entity.getAds() != null
                ? entity.getAds().stream().map(AdEntity::getAdId).toList()
                : List.of();

        return Campaign.builder()
                .campaignId(entity.getCampaignId())
                .name(entity.getName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .active(entity.isActive())
                .clientId(entity.getClient().getClientId())
                .platforms(entity.getPlatforms())
                .adIds(adIds)
                .build();
    }

    static Client toClientModel(ClientEntity client) {
        List<Long> id = client.getCampaigns().stream().map(CampaignEntity::getCampaignId).collect(Collectors.toList());
        return Client.builder().clientId(client.getClientId()).name(client.getName()).contactInfo(client.getContactInfo()).campaignIds(id).build();
    }
}
