package com.lti.movies.controller;

import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.service.MovieRatingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movieRatings")
public class MovieRatingController {

    private final MovieRatingService movieRatingService;

    public MovieRatingController(MovieRatingService movieRatingService) {
        this.movieRatingService = movieRatingService;
    }

    @Operation(summary = "Positive movie rating ")
    @PostMapping("/movie/{movieId}/client/{clientId}/voteUp")
    public MovieRatingDto voteUp(@PathVariable Integer movieId, @PathVariable Integer clientId) {
        return movieRatingService.voteUp(movieId, clientId);
    }

    @Operation(summary = "Negative movie rating ")
    @PostMapping("/movie/{movieId}/client/{clientId}/voteDown")
    public MovieRatingDto voteDown(@PathVariable Integer movieId, @PathVariable Integer clientId) {
        return movieRatingService.voteDown(movieId, clientId);
    }
}
