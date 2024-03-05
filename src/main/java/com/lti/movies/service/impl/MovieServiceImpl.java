package com.lti.movies.service.impl;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.entity.Movie;
import com.lti.movies.entity.ReleaseYear;
import com.lti.movies.exception.MovieCatalogException;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.repository.MovieRepository;
import com.lti.movies.repository.ReleaseYearRepository;
import com.lti.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ReleaseYearRepository releaseYearRepository;
    private final MapStructMapper mapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ReleaseYearRepository releaseYearRepository, MapStructMapper mapper) {
        this.movieRepository = movieRepository;
        this.releaseYearRepository = releaseYearRepository;
        this.mapper = mapper;
    }

    @Override
    public MovieDto createMovie(MovieDto movieDto) {
        //check if year exists
        ReleaseYear releaseYear = releaseYearRepository.findByYear(movieDto.getYear());
        if (releaseYear == null) {
            //create  new year
            releaseYear = releaseYearRepository.save(new ReleaseYear(movieDto.getYear()));
        }
        Movie movie = mapper.movieDtoToMovie(movieDto);
        movie.setReleaseYear(releaseYear);
        return mapper.movieToMovieDto(movieRepository.save(movie));
    }

    @Override
    public MovieDto getMovieById(Integer movieId) {
        return mapper.movieToMovieDto(findMovieById(movieId));
    }

    @Override
    public MovieDto addClientRating(Integer movieId, Integer clientRating) {
        Movie movie = modifyMovieRating(movieId, clientRating);
        return mapper.movieToMovieDto(movieRepository.save(movie));
    }

    Movie modifyMovieRating(Integer movieId, int vote) {
        Movie movie = findMovieById(movieId);
        Integer rating = movie.getRating();
        int currentRating = rating == null ? 0 : rating;
        movie.setRating(currentRating + vote);
        movie.setLastRatingDate(new Date());
        return movie;
    }

    private Movie findMovieById(Integer movieId) {
        return movieRepository.findById(movieId).orElseThrow(() -> new MovieCatalogException("Movie does not exist"));
    }
}
