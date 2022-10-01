package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Client;
import ru.otus.repository.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class ClientsController {
    private static final Logger LOG = LoggerFactory.getLogger(ClientsController.class);

    private final ClientRepository clientRepository;

    public ClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        Random r = new Random();
        List<Client> allClients = clientRepository.findAll();
        Client randomClient = allClients.get(r.nextInt(0, allClients.size()-1));
        model.addAttribute("randomClient", randomClient);
        model.addAttribute("clients", allClients);
        return "clients";
    }

    @GetMapping("/api/client/{id}")
    public ResponseEntity<Client> getClient(@PathVariable("id") Long id) {
        Optional<Client> client = clientRepository.findById(id);
        LOG.info("Received GET client request client_id: [{}]", id);
        if (client.isPresent()) {
            return new ResponseEntity<>(client.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(path="/api/client", consumes="application/json", produces="application/json")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        LOG.info("Received POST request add client: [{}]", client);
        Client saved = clientRepository.save(client);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

}