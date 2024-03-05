package com.lti.movies.service;

import com.lti.movies.dto.ClientDto;

import java.util.List;

public interface ClientService {

    ClientDto createClient(ClientDto clientDto);

    List<ClientDto> findAllClients();
}
