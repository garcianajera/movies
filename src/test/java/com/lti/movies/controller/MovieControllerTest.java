package com.lti.movies.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lti.movies.dto.MovieDto;
import com.lti.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URL;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = MovieController.class)
class MovieControllerTest {
    private static final String API_MOVIES_BASE_URL = "/api/movies";
    private static final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private MovieService movieService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getGetMovie() throws Exception {
        MovieDto movieExpected = new MovieDto();
        movieExpected.setTitle("Minions");
        int movieId = 123;
        movieExpected.setId(movieId);
        int year = 2020;
        movieExpected.setYear(year);
        movieExpected.setRating(1);

        Mockito.when(movieService.getMovieById(movieId)).thenReturn(movieExpected);

        mockMvc.perform(get(API_MOVIES_BASE_URL + "/{id}", movieId)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$.id", is(movieId)))//
                .andExpect(jsonPath("$.title", is("Minions")))
                .andExpect(jsonPath("$.year", is(year)))
                .andExpect(jsonPath("$.rating", is(1)));

        Mockito.verify(movieService, Mockito.times(1)).getMovieById(movieId);
    }

    @Test
    void createMovie() throws Exception {

        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("New Title");
        int year = 2010;
        movieDto.setYear(year);
        movieDto.setRating(0);
        movieDto.setUrl(new URL("http://movie.com"));

        MovieDto moviePersistedDto = new MovieDto();
        moviePersistedDto.setTitle("New Title");
        moviePersistedDto.setYear(year);
        int movieId = 1;
        moviePersistedDto.setId(movieId);
        moviePersistedDto.setRating(0);

        Mockito.when(movieService.createMovie(Mockito.any(MovieDto.class))).thenReturn(moviePersistedDto);

        String json = mapper.writeValueAsString(movieDto);

        mockMvc.perform(post(API_MOVIES_BASE_URL).contentType(MediaType.APPLICATION_JSON).content(json))//
                .andExpect(status().isCreated()) //
                .andExpect(jsonPath("$.id", is(movieId))) //
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.year", is(year)))
                .andExpect(jsonPath("$.rating", is(0)));
    }
}