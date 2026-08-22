package com.tokio.filme.repositories;

import com.tokio.filme.entities.Person;
import com.tokio.filme.enuns.TypePerson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person,Long> {

    Optional<Person> findByNameIgnoreCaseAndSurnameIgnoreCase(String name, String surname);
    List<Person> findByTypes(TypePerson type);
}
