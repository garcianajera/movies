package com.lti.movies.service.impl;

import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.entity.Client;
import com.lti.movies.entity.Movie;
import com.lti.movies.entity.MovieRating;
import com.lti.movies.entity.MovieRatingKey;
import com.lti.movies.exception.MovieCatalogException;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.repository.ClientRepository;
import com.lti.movies.repository.MovieRatingRepository;
import com.lti.movies.repository.MovieRepository;
import com.lti.movies.service.MovieRatingService;
import com.lti.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MovieRatingServiceImpl implements MovieRatingService {
    private final MovieRatingRepository movieRatingRepository;
    private final MovieRepository movieRepository;
    private final ClientRepository clientRepository;
    private final MapStructMapper mapper;
    private final MovieService movieService;

    @Autowired
    public MovieRatingServiceImpl(MovieRatingRepository movieRatingRepository, MovieRepository movieRepository, ClientRepository clientRepository, MapStructMapper mapper, MovieService movieService) {
        this.movieRatingRepository = movieRatingRepository;
        this.movieRepository = movieRepository;
        this.clientRepository = clientRepository;
        this.mapper = mapper;
        this.movieService = movieService;
    }

    @Override
    public MovieRatingDto voteUp(Integer movieId, Integer clientId) {
        return modifyMovieRating(movieId, clientId, +1);
    }

    @Override
    public MovieRatingDto voteDown(Integer movieId, Integer clientId) {
        return modifyMovieRating(movieId, clientId, -1);
    }

    private MovieRatingDto modifyMovieRating(Integer movieId, Integer clientId, int vote) {
        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        Optional<Movie> movie = movieRepository.findById(movieRatingKey.getMovieId());
        Optional<Client> client = clientRepository.findById(movieRatingKey.getClientId());
        validateMovieRating(movieRatingKey, movie, client);
        MovieRating movieRating = updateMovieRating(movieRatingKey, vote, client.get(), movie.get());
        return mapper.movieRatingToDto(movieRatingRepository.save(movieRating));
    }

    private MovieRating updateMovieRating(MovieRatingKey movieRatingKey, int vote, Client client, Movie movie) {
        MovieRating movieRating = buildMovieRating(movieRatingKey, vote);
        movieRating.setClient(client);
        movieRating.setMovie(movie);
        movieService.addClientRating(movie.getId(), movieRating.getClientRating());
        return movieRating;
    }

    MovieRating buildMovieRating(MovieRatingKey movieRatingKey, Integer vote) {
        MovieRating movieRating = new MovieRating();
        movieRating.setId(movieRatingKey);
        movieRating.setClientRating(vote);
        movieRating.setRatingDate(new Date());
        return movieRating;
    }

    void validateMovieRating(MovieRatingKey movieRatingKey, Optional<Movie> movieOptional, Optional<Client> clientOptional) {
        if (movieOptional.isEmpty() || clientOptional.isEmpty()) {
            throw new MovieCatalogException("Movie and/or Client not Valid");
        }
        if (movieRatingRepository.findById(movieRatingKey).isPresent()) {
            throw new MovieCatalogException("Client already voted for that movie");
        }
    }
}
