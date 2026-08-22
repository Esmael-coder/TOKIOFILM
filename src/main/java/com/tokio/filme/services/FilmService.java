package com.tokio.filme.services;

import com.tokio.filme.dtos.FilmDTO;
import com.tokio.filme.dtos.FilmRegisterDTO;
import com.tokio.filme.entities.Person;
import com.tokio.filme.entities.Score;
import com.tokio.filme.exceptions.SaveException;
import com.tokio.filme.repositories.PersonRepository;
import com.tokio.filme.security.AuthenticatedUser;
import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.User;
import com.tokio.filme.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {


    private final FilmRepository filmRepository;
    private final AuthenticatedUser authenticatedUser;
    private final PersonRepository personRepository;

    public String saveFilm(FilmRegisterDTO dto, MultipartFile file) throws IOException {

        // TODO: implementar exception
        log.info("Tentando salvar novo filme: {}", dto.getTitle());
        User user = authenticatedUser.getAuthenticatedUser();

        Film film = new Film();
        film.setUser(user);
        film.setDuration(dto.getDuration());
        film.setYear(dto.getYear());
        film.setTitle(dto.getTitle());
        film.setSynopsis(dto.getSynopsis());
        film.setMigrated(false);

        //SALVANDO O POSTER DO FILME

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/films");

        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING
        );
        film.setPoster(fileName);

        Person director = personRepository.findById(dto.getDirectorId())
                .orElseThrow();
        film.setDirector(director);

        Person fotografo = personRepository.findById(dto.getFotografoId())
                .orElseThrow();
        film.setFotografo(fotografo);

        //BUSCANDO TODOS OS ACTORES PELO ID e adicionando ao filme;

        Set<Person> actors = new HashSet<>();
        dto.getActorsId().forEach(actorId -> {
            Person actor = personRepository.findById(actorId)
                    .orElseThrow();
            actors.add(actor);
        });
        film.setActors(actors);

        //BUSCANDO TODOS OS GUIONISTAS PELO ID e adicionando ao filme;

        Set<Person> guionistas = new HashSet<>();
        dto.getGuionistasId().forEach(guionistaId -> {
            Person guionista = personRepository.findById(guionistaId)
                    .orElseThrow();
            guionistas.add(guionista);
        });
        film.setGuionistas(guionistas);

        //BUSCANDO TODOS OS MUSICOS PELO ID e adicionando ao filme;

        Set<Person> musicos = new HashSet<>();
        dto.getMusicosId().forEach(musicoId -> {
            Person musico = personRepository.findById(musicoId)
                    .orElseThrow();
            musicos.add(musico);
        });
        film.setMusicos(musicos);

        try {
            filmRepository.save(film);
        } catch (RuntimeException e){
            log.warn("Nao foi possível salvar filme: {}", film.getTitle(), e);
            throw new SaveException("Ocorreu um erro ao salvar o filme, Tente novamente.");
        }
        return "Filme salvo com sucesso";
    }


    public List<Film> getAllFilm(){

        List<Film> films;


        log.info("Tentando carregar todos os filmes");
        try {
            films = filmRepository.findAll();
            log.info("Filmes carregados com sucesso");

        } catch (RuntimeException e) {
            log.warn("Erro ao carregar filme");
            throw new RuntimeException("Erro ao carregar filmes",e);
        }

        return films;
    }

    public List<FilmDTO> findByTitle(String title){
        List<FilmDTO> dtos = new ArrayList<>();
        try {
            List<Film> films = filmRepository.findByTitleContainingIgnoreCase(title);

            for (Film film : films){

                FilmDTO dto = new FilmDTO();
                dto.setId(film.getId());
                dto.setTitle(film.getTitle());
                dto.setYear(film.getYear());
                dto.setSynopsis(film.getSynopsis());
                dto.setDuration(film.getDuration());
                dto.setPoster(film.getPoster());
                dto.setActors(film.getActors());
                dto.setDirector(film.getDirector());
                dto.setMusicos(film.getMusicos());
                dto.setFotografo(film.getFotografo());
                dto.setGuionistas(film.getGuionistas());
                dto.setScores(film.getScores());

                dtos.add(dto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Nenhum resultado encontrado", e);
        }

        return dtos;
    }

    public FilmDTO findById(Long id){

        FilmDTO dto = new FilmDTO();

        try {
            Film film = filmRepository.findById(id)
                    .orElseThrow();

            dto.setId(film.getId());
            dto.setTitle(film.getTitle());
            dto.setYear(film.getYear());
            dto.setSynopsis(film.getSynopsis());
            dto.setDuration(film.getDuration());
            dto.setPoster(film.getPoster());
            dto.setActors(film.getActors());
            dto.setDirector(film.getDirector());
            dto.setMusicos(film.getMusicos());
            dto.setFotografo(film.getFotografo());
            dto.setGuionistas(film.getGuionistas());
            dto.setScores(film.getScores());
            dto.setReviews(film.getReviews());

        } catch (Exception e) {
            throw new RuntimeException("Nao foi possivel carregar o filme");
        }

        return dto;
    }

    public long totalFilms() {
        return filmRepository.count();
    }
}
