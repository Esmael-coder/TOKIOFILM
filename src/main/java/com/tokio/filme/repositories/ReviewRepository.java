package com.tokio.filme.repositories;

import com.tokio.filme.entities.Review;
import com.tokio.filme.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    boolean existsByFilmIdAndUserId(Long filmId, Long userId);
    List<Review> findByUser(User user);
}
