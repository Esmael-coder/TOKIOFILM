package com.tokio.filme.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int value;

    @ManyToOne // muitos scores podem pertencer a 1 filme
    @JoinColumn(name = "film_id") // nome da coluna intermadiária
    private Film film;

    @ManyToOne // muitos scores podem pertencer a 1 ‘user’
    @JoinColumn(name = "user_id")
    private User user;
}
