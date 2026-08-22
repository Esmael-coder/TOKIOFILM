package com.tokio.filme.services;

import com.tokio.filme.dtos.RestReviewDTO;
import com.tokio.filme.exceptions.ReviewNotFoundException;
import com.tokio.filme.exceptions.SaveException;
import com.tokio.filme.repositories.UserRepository;
import com.tokio.filme.security.AuthenticatedUser;
import com.tokio.filme.dtos.ReviewDTO;
import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.Review;
import com.tokio.filme.entities.User;
import com.tokio.filme.repositories.FilmRepository;
import com.tokio.filme.repositories.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AuthenticatedUser authenticatedUser;
    private final FilmRepository filmRepository;
    private final UserRepository userRepository;

    @Transactional
    public String saveReview(ReviewDTO reviewDTO, Long filmId){
        log.info("Tentando salvar review: {}", reviewDTO.getTitle());
        User user = authenticatedUser.getAuthenticatedUser();
        Film film = filmRepository.findById(filmId)
                .orElseThrow(()-> {
                    log.warn("Nao foi possivel encontrar o filme com id: {}", filmId);
                    return new RuntimeException("Filme nao encontrado");
                });

        Review review = new Review();
        review.setTitle(reviewDTO.getTitle());
        review.setTextReview(reviewDTO.getTextReview());
        review.setDate(LocalDate.now());
        review.setFilm(film);
        review.setUser(user);

        try {
            reviewRepository.save(review);
            log.info("Review salvo com sucesso: {}", reviewDTO.getTitle());
            return "Review salvo com sucesso";

        } catch (RuntimeException e) {
            log.warn("Nao foi possivel salvar Review: {}", reviewDTO.getTitle(), e);
            throw new SaveException("Ocorreu um erro ao salvar review");
        }

    }

    // Retorna todos os reviews de um user
    public List<RestReviewDTO> getAllUserReview(Long userId){

        List<Review> reviews;
        List<RestReviewDTO> restReviews = new ArrayList<>();

        User user = userRepository.findById(userId)
                .orElseThrow();

        try {
            reviews = reviewRepository.findByUser(user);

        } catch (RuntimeException e) {
            log.error("Erro ao procurar review no database", e);
            throw new RuntimeException("Ocorreu um erro no servidor.");
        }

        if (reviews.isEmpty()){
            log.info("Não foi encontrado nenhum review referente ao user: {}", user.getUsername());
            throw new ReviewNotFoundException("Nenhum review encontrado");
        }

        for (Review review : reviews){
            RestReviewDTO restReviewDTO = new RestReviewDTO();
            restReviewDTO.setId(review.getId());
            restReviewDTO.setTitle(review.getTitle());
            restReviewDTO.setDate(review.getDate());
            restReviewDTO.setUserName(review.getUser().getUsername());
            restReviewDTO.setFilmTitle(review.getFilm().getTitle());
            restReviewDTO.setTextReview(review.getTextReview());

            restReviews.add(restReviewDTO);
        }


        return restReviews;
    }

    public boolean isReviewed(Long filmId, Long userId){
        return reviewRepository.existsByFilmIdAndUserId(filmId, userId);
    }
}
