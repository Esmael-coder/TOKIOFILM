package com.tokio.filme.controllers;

import com.tokio.filme.dtos.RestReviewDTO;
import com.tokio.filme.dtos.ReviewDTO;
import com.tokio.filme.services.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    // recebe crítica de um filme
    @Operation(summary = "submete uma critica enviando id do film pela url")
    @PostMapping("/api/films/{id}/review")
    public ResponseEntity<String> saveReview(@Valid @RequestBody ReviewDTO reviewDTO, @PathVariable Long id){

        String message = reviewService.saveReview(reviewDTO, id);

        return ResponseEntity.ok().body(message);
    }

    // devolve uma lista de reviews de um determinado user
    @Operation(summary = "Devolve todos os reviews de um user")
    @GetMapping("/api/{userId}/film/review")
    public ResponseEntity<List<RestReviewDTO>> getReviews(@PathVariable Long userId){

        return ResponseEntity.ok().body(reviewService.getAllUserReview(userId));
    }


}
