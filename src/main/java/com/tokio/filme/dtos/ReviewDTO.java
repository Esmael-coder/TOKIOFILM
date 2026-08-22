package com.tokio.filme.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    @Size(min = 1, max = 200, message = "O titulo deve ter entre 1 a 30 caracteres")
    private String title;

    @Size(min = 5, max = 255, message = "Mínimo 5 e Máximo 255 caracteres")
    private String textReview;

    private LocalDate date;
    private Long filmId;
    private Long userId;
}
