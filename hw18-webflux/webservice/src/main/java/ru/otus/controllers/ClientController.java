package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.otus.dto.ClientDto;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping(value = "/web/v1/clients/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {
    private static final Logger LOG = LoggerFactory.getLogger(ClientController.class);

    WebClient client;

    public ClientController(WebClient.Builder builder,
                            @Value("${dbService.url:'http://localhost:8081/db/v1/'}") String dbServiceUrl) {
        client = builder
                .baseUrl(dbServiceUrl)
                .build();
    }

    @GetMapping(value = "/")
    public Mono<String> getClients(Model model) {
        LOG.info("Received GET all clients request");

        Random r = new Random();
        return client.get().uri("/clients/")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ClientDto>>() {})
                .doOnNext(clientsList -> model.addAttribute("clients", clientsList))
                .doOnNext(clientsList -> model.addAttribute("randomClient", clientsList.get(r.nextInt(0, clientsList.size()-1))))
                .doOnNext(clientsList -> LOG.info("Received DB response clients: [{}]", clientsList))
                .then(Mono.just("clients"));
    }

    @GetMapping(value = "/{id}")
    public Mono<ClientDto> getClient(@PathVariable("id") Long id) {
        LOG.info("Received GET client request with id: [{}]", id);

        return client.get().uri(String.format("/clients/%d", id))
                .retrieve()
                .bodyToMono(ClientDto.class)
                .doOnNext(client -> LOG.info("Received DB response client with id [{}]: [{}]", id, client));
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ClientDto>> addClient(@RequestBody ClientDto clientDto) {
        LOG.info("Received POST request to add client: [{}]", client);

        return client.post().uri("/clients/")
                .bodyValue(clientDto)
                .retrieve()
                .bodyToMono(ClientDto.class)
                .map(c -> new ResponseEntity<>(c, HttpStatus.CREATED))
                .doOnNext(client -> LOG.info("Received DB response after save client: [{}]", client));
    }
}