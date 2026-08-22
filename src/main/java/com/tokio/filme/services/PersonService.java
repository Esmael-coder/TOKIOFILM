package com.tokio.filme.services;

import com.tokio.filme.dtos.PersonLoadDTO;
import com.tokio.filme.dtos.PersonRegisterDTO;
import com.tokio.filme.entities.Person;
import com.tokio.filme.enuns.TypePerson;
import com.tokio.filme.repositories.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public String SavePerson(PersonRegisterDTO personDTO){

        log.info("Tentado cadastrar person: {}", personDTO.getName());
        Optional<Person> personExist = personRepository.findByNameIgnoreCaseAndSurnameIgnoreCase(
                personDTO.getName(),
                personDTO.getSurname());

        List<TypePerson> types = List.of(TypePerson.values());

        if (personExist.isPresent()){
            log.info("Person: {} ja existe, adicionando novo type ao person", personDTO.getName());
            Person newPerson = personExist.get();

            // type vem como String no dto e aqui transformo em Enum para guardar no database
            for (TypePerson type : types){
                if (personDTO.getType().equals(type.toString())){
                    newPerson.getTypes().add(type);
                }
            }
            try {
                personRepository.save(newPerson);
                log.info("Adicionado e salvo com sucesso");

            } catch (RuntimeException e) {
                log.warn("Erro ao salvar new TypePerson na person: {}", personDTO.getName());
                throw new RuntimeException(e);
            }

        } else {
            Person person = new Person();
            person.setName(personDTO.getName());
            person.setSurname(personDTO.getSurname());

            for (TypePerson type : types){
                if (personDTO.getType().equals(type.toString())){
                    person.getTypes().add(type);
                }
            }

            try {
                personRepository.save(person);
                log.info("person criada com sucesso");

            } catch (RuntimeException e) {
                log.warn("Erro ao salvar new Person: {}", person.getName());
                throw new RuntimeException(e.getMessage());
            }
        }

        return "Sucesso";
    }

    public List<PersonLoadDTO> getAll(){

        List<Person> persons = personRepository.findAll();
        List<PersonLoadDTO> personLoadDTOs = new ArrayList<>();

        for (Person person : persons){
            PersonLoadDTO personLoad = new PersonLoadDTO();
            personLoad.setId(person.getId());
            personLoad.setName(person.getName());
            personLoad.setSurname(person.getSurname());
            personLoad.setTypes(person.getTypes());
            personLoadDTOs.add(personLoad);
        }

        return personLoadDTOs;
    }

    public long totalPersons() {
        return personRepository.count();
    }
}
