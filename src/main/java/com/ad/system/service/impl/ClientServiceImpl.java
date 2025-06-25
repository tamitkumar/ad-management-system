package com.ad.system.service.impl;

import com.ad.system.entity.CampaignEntity;
import com.ad.system.entity.ClientEntity;
import com.ad.system.model.Client;
import com.ad.system.repository.CampaignRepository;
import com.ad.system.repository.ClientRepository;
import com.ad.system.service.ClientService;
import com.ad.system.utls.AdSystemUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ad.system.utls.AdSystemUtils.toClientModel;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CampaignRepository campaignRepository;
    @Override
    public Client createClient(Client client) {
        List<CampaignEntity> campaigns = campaignRepository.findAllByCampaignIdIn(client.getCampaignIds());
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(client.getName());
        clientEntity.setContactInfo(client.getContactInfo());
        clientEntity.setCampaigns(campaigns);
        return toClientModel(clientRepository.save(clientEntity));
    }

    @Override
    public Client getClient(Long id) {
        Optional<ClientEntity> client = clientRepository.findById(id);
        return client.map(AdSystemUtils::toClientModel).orElse(null);
    }

    @Override
    public List<Client> getAllClients() {
        List<Client> result = new ArrayList<>();
        clientRepository.findAll().forEach(c -> result.add(toClientModel(c)));
        return result;
    }
}
