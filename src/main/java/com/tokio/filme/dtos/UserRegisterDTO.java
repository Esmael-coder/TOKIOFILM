package com.tokio.filme.dtos;

import com.tokio.filme.entities.User;
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
public class UserRegisterDTO {
    private Long id;

    @NotBlank(message = "nome obrigatório")
    @Size(min = 2, max = 20, message = "min 2 e max 20 caracteres")
    private String username;

    @Size(min = 6, max = 12, message = "Mínimo 6 e maximo 12")
    private String password;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}$", message = "O nome deve conter pelo menos 2 letras e não pode ter espaços ou números")
    private String name;

    @Size(min = 2, max = 20, message = "min 2 e max 20 caracteres")
    private String surname;

    @Email(message = "Email invalido")
    private String email;

    private String role;

    @NotNull(message = "Campo obrigatório")
    @Past(message = "A data deve estar no passado")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date birthDate;

    public UserRegisterDTO(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.name = user.getName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
    }
}
