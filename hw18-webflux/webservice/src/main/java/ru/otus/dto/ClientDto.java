package ru.otus.dto;

import java.util.Set;

public class ClientDto {

    private Long id;

    private String name;

    private String address;

    private Set<String> phoneNumbers;

    public ClientDto() {}

    public ClientDto(String name, String address, Set<String> phoneNumbers) {
        this.name = name;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
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
