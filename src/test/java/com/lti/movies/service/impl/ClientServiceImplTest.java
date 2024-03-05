package com.lti.movies.service.impl;

import com.lti.movies.dto.ClientDto;
import com.lti.movies.entity.Client;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.mapper.MapStructMapperImpl;
import com.lti.movies.repository.ClientRepository;
import com.lti.movies.service.ClientService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@SpringBootTest(classes = {MapStructMapper.class, MapStructMapperImpl.class})
@RunWith(SpringRunner.class)
class ClientServiceImplTest {

    @Autowired
    private MapStructMapper mapper;

    @Test
    void createClient() {
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        ClientService clientService = new ClientServiceImpl(clientRepository, mapper);

        ClientDto clientDto = new ClientDto(0, "John");
        Client client = new Client();
        client.setId(123);
        client.setUserName("John");

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(client);
        ClientDto clientFromService = clientService.createClient(clientDto);

        Assert.assertEquals(clientDto.getUserName(), clientFromService.getUserName());
    }

    @Test
    void findAllClients() {
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        ClientService clientService = new ClientServiceImpl(clientRepository, mapper);
        Client client = new Client();
        client.setId(123);
        client.setUserName("John");

        Mockito.when(clientRepository.findAll()).thenReturn(List.of(client));
        List<ClientDto> clientDtoList = clientService.findAllClients();

        Assert.assertNotNull(clientDtoList);
        Assert.assertEquals(1, clientDtoList.size());
    }
}