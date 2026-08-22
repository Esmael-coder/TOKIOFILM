package com.tokio.filme.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateUserDTO {

    @NotBlank(message = "nome obrigatório")
    @Size(min = 2, max = 8, message = "min 2 e max 8 caracteres")
    private String username;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}$", message = "O nome deve conter pelo menos 2 letras e não pode ter espaços ou números")
    private String name;

    @Size(min = 2, max = 8, message = "min 2 e max 8 caracteres")
    private String surname;

    @Email(message = "Email invalido")
    @NotBlank(message = "preencha o email")
    private String email;


    @NotNull(message = "Campo obrigatório")
    @Past(message = "A data deve estar no passado")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;

}
