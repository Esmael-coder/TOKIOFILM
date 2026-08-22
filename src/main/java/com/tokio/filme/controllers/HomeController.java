package com.tokio.filme.controllers;

import com.tokio.filme.services.FilmService;
import com.tokio.filme.services.PersonService;
import com.tokio.filme.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final FilmService filmService;
    private final PersonService personService;

    @GetMapping("/")
    public String home(Model model){
        long totalUsers = userService.totalUsers();
        long totalFilms = filmService.totalFilms();
        long totalPersons = personService.totalPersons();

        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("totalFilms", totalFilms);
        model.addAttribute("totalPersons", totalPersons);

        return "home";
    }
}
