package com.tokio.filme.dtos;

import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScoreDTO {

    private Long id;

    @NotNull(message = "Selecione uma nota")
    private Integer value;
    private Film film;
    private User user;
}
