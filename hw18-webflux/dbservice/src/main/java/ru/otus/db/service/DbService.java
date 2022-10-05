package ru.otus.db.service;

import ru.otus.db.model.Client;
import ru.otus.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface DbService {
    Client saveClient(Client client);

    Client saveClient(ClientDto clientDto);

    List<Client> findAll();

    Optional<Client> getClient(long id);
}
