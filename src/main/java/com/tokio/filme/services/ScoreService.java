package com.tokio.filme.services;

import com.tokio.filme.dtos.ScoreDTO;
import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.Score;
import com.tokio.filme.entities.User;
import com.tokio.filme.exceptions.SaveException;
import com.tokio.filme.repositories.FilmRepository;
import com.tokio.filme.repositories.ScoreRepository;
import com.tokio.filme.security.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final FilmRepository filmRepository;
    private final AuthenticatedUser authenticatedUser;

    public String saveScore(ScoreDTO scoreDTO, Long filmId){

        Score score = new Score();

        log.info("Tentando salvar classificação...");
        score.setValue(scoreDTO.getValue());
        User user = authenticatedUser.getAuthenticatedUser();
        score.setUser(user);

        Film film = filmRepository.findById(filmId)
                .orElseThrow(()->{
                    log.warn("Nao foi possivel encontrar o filme com id: {}", filmId);
                    return new RuntimeException("Filme nao encontrado");
                });

        score.setFilm(film);

        try {
            scoreRepository.save(score);
            log.info("Classification salvo com sucesso.");
        } catch (RuntimeException e) {
            throw new SaveException("Erro ao salvar classificação. Tente novamente.");
        }

        return "Salvo com sucesso.";
    }

    public boolean isScored(Long filmId, Long userId){

        return scoreRepository.existsByFilmIdAndUserId(filmId, userId);
    }

}
