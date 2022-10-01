package ru.otus.model;

import javax.annotation.Nonnull;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

@Table("phones")
public class Phone {
    @Id
    private Long id;

    private Long clientId;

    private String number;

    public Phone() {}

    public Phone(String number) {
        this.number = number;
    }

    @PersistenceConstructor
    public Phone(Long clientId, String number) {
        this(null, clientId, number);
    }

    public Phone(Long id, Long clientId, String number) {
        this.id = id;
        this.clientId = clientId;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    @Nonnull
    public Long getClientId() {
        return clientId;
    }

    @Nonnull
    public String getNumber() {
        return number;
    }

    public void setClientId(@Nonnull Long clientId) {
        this.clientId = clientId;
    }

    public void setNumber(@Nonnull String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
