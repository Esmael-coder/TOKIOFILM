package com.tokio.filme.entities;

import com.tokio.filme.enuns.TypePerson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "persons")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;


    @Enumerated(EnumType.STRING)
    @Column(name = "type_persons")
    private Set<TypePerson> types = new HashSet<>();

}
