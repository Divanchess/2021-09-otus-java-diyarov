package ru.otus.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;
import ru.otus.model.Client;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findByName(String name);

    Optional<Client> findById(@Nonnull Long id);

    @Query("select * from clients where upper(name) = upper(:name)")
    Optional<Client> findByNameIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("update clients set name = :newName where id = :id")
    void updateName(@Param("id") @Nonnull long id, @Param("newName") String newName);

    @Override
    @Query(value = """
            select
                   c.id           as client_id,
                   c.name         as client_name,
                   a.street       as address_street,
                   p.number       as phone_number
            from clients c
                     left outer join addresses a on a.client = c.id
                     left outer join phones p on p.client_id = c.id
            order by c.id
                                                          """,
            resultSetExtractorClass = ClientResultSetExtractorClass.class)
    List<Client> findAll();
}
