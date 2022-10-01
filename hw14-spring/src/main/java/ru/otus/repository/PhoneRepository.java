package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.model.Phone;

import java.util.Optional;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {

    Optional<Phone> findById(Long id);

    Optional<Phone> findByNumberLike(@Param("number") String number);

    @Modifying
    @Query("update phones set number = :newNumber where id = :id")
    void updateNumber(@Param("id") Long id, @Param("newNumber") String newNumber);
}
