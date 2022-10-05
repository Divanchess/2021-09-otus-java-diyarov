package ru.otus.dto;

import ru.otus.db.model.Client;

import java.util.Set;

public class ClientDto {

    private Long id;

    private String name;

    private String address;

    private Set<String> phoneNumbers;

    public ClientDto() {}

    public ClientDto(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.address = client.getAddress().getStreet();
        this.phoneNumbers = client.getPhoneNumbers();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }
}
