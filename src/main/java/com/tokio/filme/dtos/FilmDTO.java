package com.tokio.filme.dtos;

import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.Person;
import com.tokio.filme.entities.Review;
import com.tokio.filme.entities.Score;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilmDTO {
    private Long id;
    private String title;
    private Integer year;
    private String duration;
    private String synopsis;
    private String poster;

    private Person director;
    private Person fotografo;
    private Set<Person> guionistas;
    private Set<Person> actors;
    private Set<Person> musicos;
    private List<Score> scores;
    private List<Review> reviews;

}
