package ru.otus.controller;

import org.springframework.http.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.otus.db.service.DbServiceImpl;
import ru.otus.dto.ClientDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/db/v1/clients/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DbClientController {
    private static final Logger LOG = LoggerFactory.getLogger(DbClientController.class);
    private final DbServiceImpl dbServiceImpl;
    private final ExecutorService executor;


    public DbClientController(DbServiceImpl dbServiceImpl) {
        this.dbServiceImpl = dbServiceImpl;
        this.executor = Executors.newFixedThreadPool(4);
    }

    @GetMapping(value = "/")
    public Mono<List<ClientDto>> getClients() {
        LOG.info("Received request to get all clients list");

        var future = CompletableFuture.supplyAsync(dbServiceImpl::findAll, executor);
        return Mono.fromFuture(future)
                .map(list -> list.stream().map(ClientDto::new).collect(Collectors.toList()))
                .doOnNext(val -> LOG.info("Found FROM DB clients: {}", val));
    }

    @GetMapping(value = "/{id}")
    public Mono<ClientDto> getClient(@PathVariable("id") Long id) {
        LOG.info("Received request to find client with id: [{}] ", id);

        var future = CompletableFuture.supplyAsync(() -> dbServiceImpl.getClient(id), executor);
        return Mono.fromFuture(future)
                .mapNotNull(c -> c.map(ClientDto::new).orElse(null))
                .doOnNext(val -> LOG.info("Found FROM DB client with id {} : {}", id, val));
    }

    @PostMapping(value = "/")
    public Mono<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        LOG.info("Received request for save client: [{}]", clientDto);

        var future = CompletableFuture.supplyAsync(() -> dbServiceImpl.saveClient(clientDto), executor);
        return Mono.fromFuture(future)
                .mapNotNull(ClientDto::new)
                .doOnNext(savedClient -> LOG.info("Saved TO DB client: " + savedClient));
    }
}