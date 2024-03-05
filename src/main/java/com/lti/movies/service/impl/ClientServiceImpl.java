package com.lti.movies.service.impl;

import com.lti.movies.dto.ClientDto;
import com.lti.movies.entity.Client;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.repository.ClientRepository;
import com.lti.movies.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final MapStructMapper mapper;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, MapStructMapper mapper) {
        this.clientRepository = clientRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = clientRepository.save(mapper.clientDtoToClient(clientDto));
        return mapper.clientToDto(client);
    }

    @Override
    public List<ClientDto> findAllClients() {
        return mapper.clientToDtoList(clientRepository.findAll());
    }
}
