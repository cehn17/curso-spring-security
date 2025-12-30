package com.cehn17.authorization_server.service;

import com.cehn17.authorization_server.exception.ObjectNotFoundException;
import com.cehn17.authorization_server.mapper.ClientAppMapper;
import com.cehn17.authorization_server.persistence.entity.security.ClientApp;
import com.cehn17.authorization_server.persistence.repository.security.ClientAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
public class RegisteredClientService implements RegisteredClientRepository {

    @Autowired
    private ClientAppRepository clientAppRepository;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        ClientApp clientApp = clientAppRepository.findByClientId(id)
                .orElseThrow(() -> new ObjectNotFoundException("Client not found"));
        return ClientAppMapper.toRegisteredClient(clientApp);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.findById(clientId);
    }
}
