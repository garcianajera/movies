package com.lti.movies.service;

import com.lti.movies.dto.MovieRatingDto;

public interface MovieRatingService {

    MovieRatingDto voteUp(Integer movieId, Integer clientId);

    MovieRatingDto voteDown(Integer movieId, Integer clientId);

}
