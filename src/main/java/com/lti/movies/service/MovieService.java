package com.lti.movies.service;

import com.lti.movies.dto.MovieDto;

public interface MovieService {

    MovieDto createMovie(MovieDto movie);

    MovieDto getMovieById(Integer movieId);

    MovieDto addClientRating(Integer movieId, Integer clientRating);
}
