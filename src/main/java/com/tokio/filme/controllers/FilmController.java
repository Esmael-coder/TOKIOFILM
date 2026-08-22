package com.tokio.filme.controllers;

import com.tokio.filme.dtos.*;
import com.tokio.filme.entities.Film;
import com.tokio.filme.entities.User;
import com.tokio.filme.enuns.TypePerson;
import com.tokio.filme.security.AuthenticatedUser;
import com.tokio.filme.services.FilmService;
import com.tokio.filme.services.PersonService;
import com.tokio.filme.services.ReviewService;
import com.tokio.filme.services.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;
    private final PersonService personService;
    private final ScoreService scoreService;
    private final ReviewService reviewService;
    private final AuthenticatedUser authenticatedUser;

    private Map<TypePerson, List<PersonLoadDTO>> getElenco() {

        List<PersonLoadDTO> persons = personService.getAll();

        Map<TypePerson, List<PersonLoadDTO>> elenco = new EnumMap<>(TypePerson.class);

        for (TypePerson type : TypePerson.values()) {

            List<PersonLoadDTO> personsByType = persons.stream()
                    .filter(person -> person.getTypes().contains(type))
                    .toList();

            elenco.put(type, personsByType);
        }

        return elenco;
    }

    @GetMapping("/film/new")
    public String showFilmForm(Model model){

        Map<TypePerson, List<PersonLoadDTO>> elenco = getElenco();

        List<PersonLoadDTO> actors = elenco.get(TypePerson.ACTOR);
        List<PersonLoadDTO> directors = elenco.get(TypePerson.DIRECTOR);
        List<PersonLoadDTO> fotografos = elenco.get(TypePerson.FOTOGRAFO);
        List<PersonLoadDTO> musicos = elenco.get(TypePerson.MUSICO);
        List<PersonLoadDTO> guionistas = elenco.get(TypePerson.GUIONISTA);


        model.addAttribute("filmDTO", new FilmRegisterDTO());
        model.addAttribute("directors", directors);
        model.addAttribute("fotografos", fotografos);
        model.addAttribute("actors", actors);
        model.addAttribute("musicos", musicos);
        model.addAttribute("guionistas", guionistas);

        return "new-film";
    }

    @PostMapping("/films/new")
    public String newFilme(Model model, @Valid @ModelAttribute("filmDTO") FilmRegisterDTO dto, BindingResult result, @RequestParam("posterFile") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {

        if (result.hasErrors()){
            Map<TypePerson, List<PersonLoadDTO>> elenco = getElenco();

            List<PersonLoadDTO> actors = elenco.get(TypePerson.ACTOR);
            List<PersonLoadDTO> directors = elenco.get(TypePerson.DIRECTOR);
            List<PersonLoadDTO> fotografos = elenco.get(TypePerson.FOTOGRAFO);
            List<PersonLoadDTO> musicos = elenco.get(TypePerson.MUSICO);
            List<PersonLoadDTO> guionistas = elenco.get(TypePerson.GUIONISTA);



            model.addAttribute("directors", directors);
            model.addAttribute("fotografos", fotografos);
            model.addAttribute("actors", actors);
            model.addAttribute("musicos", musicos);
            model.addAttribute("guionistas", guionistas);

            return "new-film";
        }

        String message = filmService.saveFilm(dto, file);

        redirectAttributes.addFlashAttribute("success",message);

        return "redirect:/";
    }

    @GetMapping("/films/search")
    public String showSearchPage(Model model) {

        model.addAttribute("films", List.of());

        return "search-film";
    }

    @GetMapping("films/search/result")
    public String SearchFilm(@RequestParam String title, Model model){

        List<FilmDTO> films = filmService.findByTitle(title);

        model.addAttribute("films", films);

        return "search-film";
    }

    @GetMapping("/films/{id}")
    public String filmDetails(@PathVariable Long id, Model model){

        User user = authenticatedUser.getAuthenticatedUser();
        boolean isScored = scoreService.isScored(id, user.getId());
        boolean isReviewed = reviewService.isReviewed(id, user.getId());

        FilmDTO film = filmService.findById(id);
        model.addAttribute("film", film);
        model.addAttribute("score", new ScoreDTO());
        model.addAttribute("reviewDTO", new ReviewDTO());
        model.addAttribute("isScored", isScored);
        model.addAttribute("isReviewed", isReviewed);


        return "film-details";
    }
}
