package com.lti.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.movies.dto.ClientDto;
import com.lti.movies.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ClientController.class)
class ClientControllerTest {

    private static final String API_CLIENT_BASE_URL = "/api/clients";
    private static final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ClientService clientService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllClients() throws Exception {
        ClientDto clientDto = new ClientDto(1, "John");

        Mockito.when(clientService.findAllClients()).thenReturn(List.of(clientDto));

        mockMvc.perform(get(API_CLIENT_BASE_URL)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$[0].userName", is("John")))
                .andExpect(jsonPath("$[0].id", is(1)));

        Mockito.verify(clientService, Mockito.times(1)).findAllClients();
    }

    @Test
    void createClient() throws Exception {
        ClientDto client = new ClientDto();
        client.setUserName("John");

        ClientDto persistedClient = new ClientDto(12, "John");

        Mockito.when(clientService.createClient(Mockito.any(ClientDto.class))).thenReturn(persistedClient);

        String json = mapper.writeValueAsString(client);

        mockMvc.perform(post(API_CLIENT_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))//
                .andExpect(status().isCreated()) //
                .andExpect(jsonPath("$.id", is(12))) //
                .andExpect(jsonPath("$.userName", is("John")));
    }
}