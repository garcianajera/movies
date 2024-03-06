package com.lti.movies.controller;

import com.lti.movies.dto.ClientDto;
import com.lti.movies.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @Operation(summary = "Returns a list of all clients")
    @ApiResponse(responseCode = "200", description = "List of clients",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ClientDto.class))})
    @GetMapping
    public List<ClientDto> getAllClients() {
        return clientService.findAllClients();
    }

    @Operation(summary = "Crate a new client")
    @ApiResponse(responseCode = "201", description = "Client is created",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ClientDto.class))})
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClientDto createClient(@Valid @RequestBody ClientDto client) {
        return clientService.createClient(client);
    }
}
