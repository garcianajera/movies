package com.lti.movies.service;

import com.lti.movies.dto.MovieRatingDto;

import java.util.List;

public interface MovieRatingService {

    MovieRatingDto voteUp(Integer movieId, Integer clientId);

    MovieRatingDto voteDown(Integer movieId, Integer clientId);

    List<MovieRatingDto> getMovieRatings();
}
