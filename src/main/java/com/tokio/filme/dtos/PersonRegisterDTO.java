package com.tokio.filme.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonRegisterDTO {


    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}$", message = "O nome deve conter pelo menos 2 letras e não pode ter espaços ou números")
    private String name;

    @Size(min = 2, max = 15, message = "Min 2 e Max 8 caracteres")
    private String surname;

    @NotBlank(message = "Selecione o trabalho")
    private String type;
    
}
