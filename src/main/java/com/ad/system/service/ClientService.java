package com.ad.system.service;

import com.ad.system.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient(Client client);
    Client getClient(Long id);
    List<Client> getAllClients();
}
