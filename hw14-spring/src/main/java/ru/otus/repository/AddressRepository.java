package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.model.Address;
import ru.otus.model.Client;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

    Optional<Address> findByClient(Long client);

    @Query("select * from address where upper(street) = like upper(%:street%)")
    Optional<Client> findByStreetLike(@Param("street") String street);

    @Modifying
    @Query("update address set street = :newStreet where client = :client")
    void updateStreet(@Param("client") Long client, @Param("newStreet") String newStreet);
}
