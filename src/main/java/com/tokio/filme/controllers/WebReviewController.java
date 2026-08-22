package com.tokio.filme.controllers;

import com.tokio.filme.dtos.FilmDTO;
import com.tokio.filme.dtos.ReviewDTO;
import com.tokio.filme.dtos.ScoreDTO;
import com.tokio.filme.entities.User;
import com.tokio.filme.security.AuthenticatedUser;
import com.tokio.filme.services.FilmService;
import com.tokio.filme.services.ReviewService;
import com.tokio.filme.services.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class WebReviewController {

    private final ReviewService reviewService;
    private final FilmService filmService;
    private final AuthenticatedUser authenticatedUser;
    private final ScoreService scoreService;

    @PostMapping("/films/{id}/review")
    public String ShowFormReview(Model model, @Valid ReviewDTO reviewDTO, BindingResult result, @PathVariable Long id, RedirectAttributes redirectAttributes){

        if (result.hasErrors()){
            User user = authenticatedUser.getAuthenticatedUser();

            FilmDTO film = filmService.findById(id);
            boolean isScored = scoreService.isScored(id, user.getId());
            boolean isReviewed = reviewService.isReviewed(id, user.getId());

            model.addAttribute("film", film);
            model.addAttribute("score", new ScoreDTO());
            model.addAttribute("isScored", isScored);
            model.addAttribute("isReviewed", isReviewed);

            return "film-details";
        }

        reviewService.saveReview(reviewDTO, id);
        redirectAttributes.addFlashAttribute("success", "Review salvo com sucesso");

        return "redirect:/films/" + id;
    }


}
