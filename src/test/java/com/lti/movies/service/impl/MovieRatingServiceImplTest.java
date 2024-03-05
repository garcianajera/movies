package com.lti.movies.service.impl;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.entity.*;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.mapper.MapStructMapperImpl;
import com.lti.movies.repository.ClientRepository;
import com.lti.movies.repository.MovieRatingRepository;
import com.lti.movies.repository.MovieRepository;
import com.lti.movies.service.MovieRatingService;
import com.lti.movies.service.MovieService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest(classes = {MapStructMapper.class, MapStructMapperImpl.class})
@RunWith(SpringRunner.class)
class MovieRatingServiceImplTest {
    @Autowired
    private MapStructMapper mapper;

    @Test
    void voteUp() {
        MovieRatingRepository movieRatingrepository = Mockito.mock(MovieRatingRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        MovieService movieService = Mockito.mock(MovieService.class);

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("title");
        movie.setReleaseYear(new ReleaseYear(2020));
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        int clientId = 567;
        Client client = new Client(clientId, "John");
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        Mockito.when(movieRatingrepository.findById(movieRatingKey)).thenReturn(Optional.empty());

        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        Mockito.when(movieService.addClientRating(movieId, 1)).thenReturn(movieDto);

        MovieRating movieRating = new MovieRating();
        movieRating.setId(movieRatingKey);
        movieRating.setClientRating(1);
        movieRating.setMovie(movie);
        movieRating.setClient(client);

        Mockito.when(movieRatingrepository.save(Mockito.any(MovieRating.class))).thenReturn(movieRating);

        MovieRatingService movieRatingService = new MovieRatingServiceImpl(movieRatingrepository, movieRepository, clientRepository, mapper, movieService);

        MovieRatingDto movieRatingDto = movieRatingService.voteUp(movieId, clientId);

        Assert.assertEquals(clientId, movieRatingDto.getClient().getId().intValue());
        Assert.assertEquals(movieId, movieRatingDto.getMovie().getId().intValue());

    }


    @Test
    void voteDown() {
        MovieRatingRepository movieRatingrepository = Mockito.mock(MovieRatingRepository.class);
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        MovieService movieService = Mockito.mock(MovieService.class);

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("title");
        movie.setReleaseYear(new ReleaseYear(2020));
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        int clientId = 567;
        Client client = new Client(clientId, "John");
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        Mockito.when(movieRatingrepository.findById(movieRatingKey)).thenReturn(Optional.empty());

        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        Mockito.when(movieService.addClientRating(movieId, -1)).thenReturn(movieDto);

        MovieRating movieRating = new MovieRating();
        movieRating.setId(movieRatingKey);
        movieRating.setClientRating(1);
        movieRating.setMovie(movie);
        movieRating.setClient(client);

        Mockito.when(movieRatingrepository.save(Mockito.any(MovieRating.class))).thenReturn(movieRating);

        MovieRatingService movieRatingService = new MovieRatingServiceImpl(movieRatingrepository, movieRepository, clientRepository, mapper, movieService);

        MovieRatingDto movieRatingDto = movieRatingService.voteDown(movieId, clientId);

        Assert.assertEquals(clientId, movieRatingDto.getClient().getId().intValue());
        Assert.assertEquals(movieId, movieRatingDto.getMovie().getId().intValue());
    }

    @Test
    void validateMovieRating_clientNotExist() {

        int clientId = 567;

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("title");
        movie.setReleaseYear(new ReleaseYear(2020));


        MovieRatingServiceImpl movieRatingService = new MovieRatingServiceImpl(null, null, null, null, null);

        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        Exception exception = Assert.assertThrows(RuntimeException.class, () -> movieRatingService.validateMovieRating(movieRatingKey, Optional.of(movie), Optional.empty()));

        Assert.assertEquals("Movie and/or Client not Valid", exception.getMessage());
    }

    @Test
    void validateMovieRating_movieNotExist() {

        int clientId = 567;
        Client client = new Client(clientId, "John");

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("title");
        movie.setReleaseYear(new ReleaseYear(2020));


        MovieRatingServiceImpl movieRatingService = new MovieRatingServiceImpl(null, null, null, null, null);

        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        Exception exception = Assert.assertThrows(RuntimeException.class, () -> movieRatingService.validateMovieRating(movieRatingKey, Optional.empty(), Optional.of(client)));

        Assert.assertEquals("Movie and/or Client not Valid", exception.getMessage());
    }

    @Test
    void validateMovieRating_clientAlreadyVoted() {
        MovieRepository movieRepository = Mockito.mock(MovieRepository.class);
        ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
        MovieRatingRepository movieRatingrepository = Mockito.mock(MovieRatingRepository.class);

        int clientId = 567;
        Client client = new Client(clientId, "John");
        Mockito.when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Movie movie = new Movie();
        int movieId = 123;
        movie.setId(movieId);
        movie.setTitle("title");
        movie.setReleaseYear(new ReleaseYear(2020));
        Mockito.when(movieRepository.findById(movieId)).thenReturn(Optional.of(movie));

        MovieRatingKey movieRatingKey = new MovieRatingKey(movieId, clientId);
        MovieRating movieRating = new MovieRating();
        movieRating.setId(movieRatingKey);
        movieRating.setClientRating(1);
        movieRating.setMovie(movie);
        movieRating.setClient(client);

        Mockito.when(movieRatingrepository.findById(Mockito.any(MovieRatingKey.class))).thenReturn(Optional.of(movieRating));
        MovieRatingServiceImpl movieRatingService = new MovieRatingServiceImpl(movieRatingrepository, movieRepository, clientRepository, null, null);

        Exception exception = Assert.assertThrows(RuntimeException.class, () -> movieRatingService.validateMovieRating(movieRatingKey, Optional.of(movie), Optional.of(client)));

        Assert.assertEquals("Client already voted for that movie", exception.getMessage());
    }


}