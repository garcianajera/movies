package com.lti.movies.controller;

import com.lti.movies.dto.ClientDto;
import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.service.MovieRatingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieRatingController.class)
class MovieRatingControllerTest {
    private static final String API_MOVIES_BASE_URL = "/api/movieRatings";
    @MockBean
    private MovieRatingService movieRatingService;

    @Autowired
    private MockMvc mockMvc;

    private static MovieRatingDto buildMovieRating(Date today, int movieId, int clientId) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setRating(10);
        movieDto.setTitle("Rambo");

        ClientDto clientDto = new ClientDto(clientId, "John");

        MovieRatingDto movieRating = new MovieRatingDto();
        movieRating.setClient(clientDto);
        movieRating.setMovie(movieDto);
        movieRating.setRatingDate(today);
        movieRating.setClientRating(1);
        return movieRating;
    }

    @Test
    void voteUp() throws Exception {
        Date today = new Date();
        int movieId = 12;
        int clientId = 123;

        MovieRatingDto movieRating = buildMovieRating(today, movieId, clientId);

        Mockito.when(movieRatingService.voteUp(movieId, clientId)).thenReturn(movieRating);

        mockMvc.perform(post(API_MOVIES_BASE_URL + "/movie/{movieId}/client/{clientId}/voteUp", movieId, clientId))//
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.movie.id", is(12))) //
                .andExpect(jsonPath("$.movie.title", is("Rambo")))
                .andExpect(jsonPath("$.movie.rating", is(10)))
                .andExpect(jsonPath("$.client.id", is(123)))
                .andExpect(jsonPath("$.client.userName", is("John")))
                .andExpect(jsonPath("$.clientRating", is(1)));
    }

    @Test
    void voteDown() throws Exception {
        Date today = new Date();
        int movieId = 12;
        int clientId = 123;
        MovieRatingDto movieRating = buildMovieRating(today, movieId, clientId);
        Mockito.when(movieRatingService.voteDown(movieId, clientId)).thenReturn(movieRating);

        mockMvc.perform(post(API_MOVIES_BASE_URL + "/movie/{movieId}/client/{clientId}/voteDown", movieId, clientId))//
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.movie.id", is(12))) //
                .andExpect(jsonPath("$.movie.title", is("Rambo")))
                .andExpect(jsonPath("$.movie.rating", is(10)))
                .andExpect(jsonPath("$.client.id", is(123)))
                .andExpect(jsonPath("$.client.userName", is("John")))
                .andExpect(jsonPath("$.clientRating", is(1)));
    }

    @Test
    public void testGetAllMovieRatings() throws Exception {
        Date today = new Date();
        int movieId = 12;
        int clientId = 123;
        MovieRatingDto movieRating = buildMovieRating(today, movieId, clientId);

        Mockito.when(movieRatingService.getMovieRatings()).thenReturn(List.of(movieRating));

        mockMvc.perform(get(API_MOVIES_BASE_URL)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$[0].movie.id", is(12))) //
                .andExpect(jsonPath("$[0].movie.title", is("Rambo")))
                .andExpect(jsonPath("$[0].movie.rating", is(10)))
                .andExpect(jsonPath("$[0].client.id", is(123)))
                .andExpect(jsonPath("$[0].client.userName", is("John")))
                .andExpect(jsonPath("$[0].clientRating", is(1)));

        Mockito.verify(movieRatingService, Mockito.times(1)).getMovieRatings();

    }

}