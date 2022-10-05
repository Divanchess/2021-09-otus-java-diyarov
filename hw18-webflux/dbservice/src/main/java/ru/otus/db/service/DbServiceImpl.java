package ru.otus.db.service;

import ru.otus.db.model.Address;
import ru.otus.db.model.Client;
import ru.otus.db.model.Phone;
import ru.otus.db.repository.ClientRepository;
import ru.otus.db.sessionmanager.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.otus.dto.ClientDto;

import java.util.*;
import java.util.stream.Collectors;

@Service("dbService")
public class DbServiceImpl implements DbService {
    private static final Logger LOG = LoggerFactory.getLogger(DbServiceImpl.class);

    private final TransactionManager transactionManager;
    private final ClientRepository clientRepository;

    public DbServiceImpl(TransactionManager transactionManager, ClientRepository clientRepository) {
        this.transactionManager = transactionManager;
        this.clientRepository = clientRepository;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(() -> {
            var savedClient = clientRepository.save(client);
            LOG.info("DBService save client: [{}]", savedClient);
            return savedClient;
        });
    }

    @Override
    public Client saveClient(ClientDto clientDto) {
        var name = clientDto.getName();
        var street = clientDto.getAddress();
        var phoneNumbers = clientDto.getPhoneNumbers();
        Set<Phone> phones = phoneNumbers.stream().map(Phone::new).collect(Collectors.toSet());

        var client = new Client(name, new Address(street), phones);
        return saveClient(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        var client = clientRepository.findById(id);
        LOG.info("DBService get client: [{}]", client);
        return client;
    }

    @Override
    public List<Client> findAll() {
        var clientList = clientRepository.findAll();
        LOG.info("DBService find all clientList: [{}]", clientList);
        return clientList;
    }
}
