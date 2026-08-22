package com.tokio.filme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table( // serve para configurar a tabela.
        name = "reviews",
        uniqueConstraints = { //declarando uma ou mais restrições de unicidade
                @UniqueConstraint( //Cria uma regra de unicidade (A combinação das colunas user_id e film_id deve ser única).
                        columnNames = {"user_id", "film_id"}
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String textReview;
    @CreationTimestamp
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
