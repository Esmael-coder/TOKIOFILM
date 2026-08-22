package com.tokio.filme.controllers;

import com.tokio.filme.dtos.PersonRegisterDTO;
import com.tokio.filme.enuns.TypePerson;
import com.tokio.filme.services.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;


    public List<TypePerson> allPersons(){
        // vai trazer todos os types
        return List.of(TypePerson.values());
    }

    @GetMapping("/persons/new")
    public String ShowPersonForm(Model model){
        model.addAttribute("personRegisterDTO", new PersonRegisterDTO());
        model.addAttribute("typePersons", allPersons());
        return "new-person";
    }

    @PostMapping("/persons/new")
    public String CreatePerson(@Valid PersonRegisterDTO personRegisterDTO, BindingResult result, Model model, RedirectAttributes redirectAttributes){

        if (result.hasErrors()){
            model.addAttribute("typePersons", allPersons());
            return "new-person";
        }

        String message = personService.SavePerson(personRegisterDTO);

        redirectAttributes.addFlashAttribute("success", message);

        return "redirect:/";
    }
}
