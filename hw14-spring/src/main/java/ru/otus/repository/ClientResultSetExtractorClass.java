package ru.otus.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientResultSetExtractorClass implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        Long prevClientId = null;
        Client client = null;
        while (rs.next()) {
            Long clientId = Long.valueOf(rs.getString("client_id"));
            if (prevClientId == null || !prevClientId.equals(clientId)) {
                client = new Client(clientId, rs.getString("client_name"), new Address(rs.getString("address_street")), new HashSet<>(), false);
                clientList.add(client);
                prevClientId = clientId;
            }
            String phoneNumber = (String) rs.getObject("phone_number");
            if (client != null && phoneNumber != null) {
                client.getPhones().add(
                        new Phone(clientId, phoneNumber));
            }
        }
        return clientList;
    }
}
