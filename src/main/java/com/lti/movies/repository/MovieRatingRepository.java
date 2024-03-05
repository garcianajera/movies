package com.lti.movies.repository;

import com.lti.movies.entity.MovieRating;
import com.lti.movies.entity.MovieRatingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRating, MovieRatingKey> {
}
