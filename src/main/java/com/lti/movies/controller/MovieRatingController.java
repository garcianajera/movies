package com.lti.movies.controller;

import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.service.MovieRatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/movieRatings")
@Tag(name = "Movies Ratings")
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

    @Operation(summary = "Get all movie ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found movie ratings",
                    content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MovieRatingDto.class))})})
    @GetMapping()
    public List<MovieRatingDto> getMovieRatingsByMovie() {
        return movieRatingService.getMovieRatings();
    }

}
