package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("addresses")
public class Address {
    @Id
    private Long client;

    private String street;

    public Address() {}

    public Address(String street) {
        this.street = street;
    }

    @PersistenceConstructor
    public Address(Long client, String street) {
        this.client = client;
        this.street = street;
    }

    @Nonnull
    public Long getClient() {
        return client;
    }

    public void setClient(@Nonnull Long client) {
        this.client = client;
    }

    @Nonnull
    public String getStreet() {
        return street;
    }

    public void setStreet(@Nonnull String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                ", client='" + client + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
