package ru.otus.dao;

import ru.otus.database.crm.model.Client;

import java.util.Optional;

public interface ClientDao {

    Optional<Client> findById(long id);
    Optional<Client> findRandomClient();
    Optional<Client> findByName(String name);
}