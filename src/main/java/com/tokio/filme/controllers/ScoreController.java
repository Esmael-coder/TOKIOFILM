package com.tokio.filme.controllers;

import com.tokio.filme.dtos.ScoreDTO;
import com.tokio.filme.services.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping("films/{id}/rate")
    public String saveScore(@PathVariable Long id, ScoreDTO scoreDTO, RedirectAttributes redirectAttributes){

        String message = scoreService.saveScore(scoreDTO, id);

        redirectAttributes.addFlashAttribute("success", message);

        return "redirect:/films/" + id;
    }
}
