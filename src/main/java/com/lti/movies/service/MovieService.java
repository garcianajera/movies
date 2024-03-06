package com.lti.movies.service;

import com.lti.movies.dto.MovieDto;

import java.util.List;

public interface MovieService {

    MovieDto createMovie(MovieDto movie);

    MovieDto getMovieById(Integer movieId);

    MovieDto addClientRating(Integer movieId, Integer clientRating);

    List<MovieDto> getAllMovies();
}
