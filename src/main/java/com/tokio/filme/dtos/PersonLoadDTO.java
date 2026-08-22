package com.tokio.filme.dtos;

import com.tokio.filme.enuns.TypePerson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonLoadDTO {

    private Long id;
    private String name;
    private String surname;
    private Set<TypePerson> types;
    
}
