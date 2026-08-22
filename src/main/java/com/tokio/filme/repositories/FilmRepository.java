package com.tokio.filme.repositories;

import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Long> {

    List<Film> findByTitleContainingIgnoreCase(String title);
    long countByMigratedFalse();
}
