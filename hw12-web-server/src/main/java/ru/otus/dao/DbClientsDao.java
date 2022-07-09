package ru.otus.dao;

import ru.otus.database.crm.model.Address;
import ru.otus.database.crm.model.Client;
import ru.otus.database.crm.model.Phone;

import java.util.*;

public class DbClientsDao implements ClientDao {

    private final Map<Long, Client> clients;

    public DbClientsDao() {
        clients = new HashMap<>();
        clients.put(1L, new Client(1L, "Client1", new Address(1L, "345345"), List.of(new Phone(1L, "123123"))));
        clients.put(2L, new Client(2L, "Client2", new Address(2L, "345345"), List.of(new Phone(2L, "123123"))));
        clients.put(3L, new Client(3L, "Client3", new Address(3L, "345345"), List.of(new Phone(3L, "123123"))));
        clients.put(4L, new Client(4L, "Client4", new Address(4L, "345345"), List.of(new Phone(4L, "123123"))));
        clients.put(5L, new Client(5L, "Client5", new Address(5L, "345345"), List.of(new Phone(5L, "123123"))));
        clients.put(6L, new Client(6L, "Client6", new Address(6L, "345345"), List.of(new Phone(6L, "123123"))));
        clients.put(7L, new Client(7L, "Client7", new Address(7L, "345345"), List.of(new Phone(7L, "123123"))));
    }

    @Override
    public Optional<Client> findById(long id) {
        return Optional.ofNullable(clients.get(id));
    }

    @Override
    public Optional<Client> findRandomClient() {
        Random r = new Random();
        return clients.values().stream().skip(r.nextInt(clients.size() - 1)).findFirst();
    }

    @Override
    public Optional<Client> findByName(String name) {
        return clients.values().stream().filter(v -> v.getName().equals(name)).findFirst();
    }
}
