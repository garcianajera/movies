package com.lti.movies.service.impl;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.entity.Movie;
import com.lti.movies.entity.ReleaseYear;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.mapper.MapStructMapperImpl;
import com.lti.movies.repository.MovieRepository;
import com.lti.movies.repository.ReleaseYearRepository;
import com.lti.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MapStructMapper.class, MapStructMapperImpl.class})
@RunWith(SpringRunner.class)
class MovieServiceImplTest {
    @Autowired
    private MapStructMapper mapper;

    @Test
    void createMovie() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        ReleaseYearRepository releaseYearRepository = Mockito.mock(ReleaseYearRepository.class);

        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);
        Mockito.when(releaseYearRepository.findByYear(year)).thenReturn(releaseYear);
        Movie movie = new Movie();
        movie.setId(123);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);
        MovieService movieService = new MovieServiceImpl(movieRepository, releaseYearRepository, mapper);

        MovieDto movieToCreate = new MovieDto();
        movieToCreate.setYear(year);
        movieToCreate.setTitle("Rambo");
        MovieDto movieDto = movieService.createMovie(movieToCreate);

        assertEquals("Rambo", movieDto.getTitle());
        assertEquals(123, movieDto.getId());
        assertEquals(2020, movieDto.getYear());
    }

    @Test
    void createMovie_newReleaseYear() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        ReleaseYearRepository releaseYearRepository = Mockito.mock(ReleaseYearRepository.class);

        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);
        Mockito.when(releaseYearRepository.findByYear(year)).thenReturn(null);
        Movie movie = new Movie();
        movie.setId(123);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movie);
        Mockito.when(releaseYearRepository.save(Mockito.any(ReleaseYear.class))).thenReturn(releaseYear);
        MovieService movieService = new MovieServiceImpl(movieRepository, releaseYearRepository, mapper);

        MovieDto movieToCreate = new MovieDto();
        movieToCreate.setYear(year);
        movieToCreate.setTitle("Rambo");
        MovieDto movieDto = movieService.createMovie(movieToCreate);

        assertEquals("Rambo", movieDto.getTitle());
        assertEquals(123, movieDto.getId());
        assertEquals(2020, movieDto.getYear());
    }

    @Test
    void getMovieById() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));
        MovieService movieService = new MovieServiceImpl(movieRepository, null, mapper);

        MovieDto movieDto = movieService.getMovieById(movieId);

        assertEquals("Rambo", movieDto.getTitle());
        assertEquals(123, movieDto.getId());
        assertEquals(2020, movieDto.getYear());
    }

    @Test
    void getAllMovies() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        Mockito.when(movieRepository.findAll()).thenReturn(List.of(movie));
        MovieService movieService = new MovieServiceImpl(movieRepository, null, mapper);

        List<MovieDto> movieDtoList = movieService.getAllMovies();

        assertEquals("Rambo", movieDtoList.get(0).getTitle());
        assertEquals(123, movieDtoList.get(0).getId());
        assertEquals(2020, movieDtoList.get(0).getYear());
    }

    @Test
    void addClientRating() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);
        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        movie.setRating(10);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        Movie movieModified = new Movie();
        movieModified.setId(movieId);
        movieModified.setTitle("Rambo");
        movieModified.setReleaseYear(releaseYear);
        movieModified.setRating(11);
        Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(movieModified);

        MovieService movieService = new MovieServiceImpl(movieRepository, null, mapper);

        MovieDto movieDto = movieService.addClientRating(movieId, 1);

        assertEquals("Rambo", movieDto.getTitle());
        assertEquals(123, movieDto.getId());
        assertEquals(2020, movieDto.getYear());
        assertEquals(11, movieDto.getRating());
    }

    @Test
    public void testModifyMovieRating() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        Integer year = 2020;
        ReleaseYear releaseYear = new ReleaseYear(year);
        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("Rambo");
        movie.setReleaseYear(releaseYear);
        movie.setRating(10);
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieServiceImpl movieService = new MovieServiceImpl(movieRepository, null, mapper);

        assertEquals(11, movieService.modifyMovieRating(movieId, 1).getRating());
    }
}