package com.tokio.filme.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmRegisterDTO {
    private Long id;

    @Size(min = 1, max = 200, message = "O titulo deve ter entre 1 a 200 caracteres")
    private String title;

    @Min(value = 1888, message = "Ano invalido")
    @Max(value = 2100, message = "Ano invalido")
    @NotNull(message = "Campo obrigatório")
    private Integer year;

    @NotBlank(message = "Campo obrigatório")
    private String duration;

    @Size(min = 10, max = 500, message = "A sinopse deve ter entre 10 a 500 caracteres")
    private String synopsis;

    @NotNull(message = "Seleciona um Director")
    private Long directorId;

    @NotNull(message = "Seleciona um Fotógrafo")
    private Long fotografoId;

    @NotEmpty(message = "Seleciona pelo menos um guionista")
    private Set<Long> guionistasId;

    @NotEmpty(message = "Seleciona pelo menos um actor")
    private Set<Long> actorsId;

    @NotEmpty(message = "Seleciona pelo menos um músico")
    private Set<Long> musicosId;

}
