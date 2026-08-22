package com.tokio.filme.entities;

import com.tokio.filme.dtos.FilmDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "film")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer year;
    private String duration;
    private String poster;
    private String synopsis;
    private boolean migrated;
    private LocalDate dateMigrate;

    @ManyToOne // muitos filmes podem pertencer a 1 ‘user’
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private Person director;

    @ManyToOne
    private Person fotografo;

    @ManyToMany
    private Set<Person> guionistas = new HashSet<>();

    @ManyToMany
    private Set<Person> actors = new HashSet<>();

    @ManyToMany
    private Set<Person> musicos = new HashSet<>();


    @OneToMany(mappedBy = "film") // 1 filme pode ter várias pontuações
    private List<Score> scores = new ArrayList<>();

    @OneToMany(mappedBy = "film")
    private List<Review> reviews;

    public Film(FilmDTO filmDTO) {
        this.title = filmDTO.getTitle();
        this.year = filmDTO.getYear();

        this.synopsis = filmDTO.getSynopsis();
        this.poster = filmDTO.getPoster();
        this.director = filmDTO.getDirector();
        this.guionistas = filmDTO.getGuionistas();
        this.actors = filmDTO.getActors();
        this.musicos = filmDTO.getMusicos();
        this.fotografo = filmDTO.getFotografo();
    }

}
