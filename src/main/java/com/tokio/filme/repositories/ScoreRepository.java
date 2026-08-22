package com.tokio.filme.repositories;

import com.tokio.filme.entities.Score;
import com.tokio.filme.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score,Long> {

    boolean existsByFilmIdAndUserId(Long filmId, Long userId);
}
