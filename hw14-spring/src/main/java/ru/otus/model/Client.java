package ru.otus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import javax.annotation.Nonnull;

@Table("clients")
public class Client implements Persistable<Long> {
    @Id
    private Long id;

    private String name;

    private Address address;

    @MappedCollection(idColumn = "client_id")
    private Set<Phone> phones;

    @Transient
    private boolean isNew = true;

    public Client() {}

    public Client(String name) {
        this(null, name, null, null, true);
    }

    public Client(String name, Address address) {
        this(null, name, address, null, true);
    }

    public Client(String name, Set<Phone> phones) {
        this(null, name, null, phones, true);
    }

    @PersistenceConstructor
    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones, true);
    }

    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, id == null);
    }

    public Client(Long id, String name, Address address, Set<Phone> phones, boolean isNew) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;
    }


    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void addPhone(Phone phone) {
        this.phones.add(phone);
    }


    @Override
    public String toString() {
        return "Client{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                ", isNew=" + isNew +
                '}';
    }
}
