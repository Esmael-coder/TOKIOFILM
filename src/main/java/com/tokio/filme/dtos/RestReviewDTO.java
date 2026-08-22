package com.tokio.filme.dtos;

import jakarta.validation.constraints.NotBlank;
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
public class RestReviewDTO {

    private Long id;
    private String title;
    private LocalDate date;
    private String userName;
    private String filmTitle;
    private String textReview;
}
