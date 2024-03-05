package com.lti.movies.controller;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/movies")
@Validated
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get a movie by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the movie", content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDto.class))}),
            @ApiResponse(responseCode = "404", description = "Movie not found", content = @Content)})
    @GetMapping("/{id}")
    public MovieDto getMovie(@PathVariable Integer id) {
        return movieService.getMovieById(id);
    }

    @Operation(summary = "Crate a new movie")
    @ApiResponse(responseCode = "201", description = "Movie is created",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = MovieDto.class))})
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public MovieDto createMovie(@RequestBody MovieDto movieDto) {
        return movieService.createMovie(movieDto);
    }

}
