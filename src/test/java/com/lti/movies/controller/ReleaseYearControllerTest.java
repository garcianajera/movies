package com.lti.movies.controller;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.ReleaseYearDto;
import com.lti.movies.service.ReleaseYearService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ReleaseYearController.class)
class ReleaseYearControllerTest {
    private static final String API_RELEASE_YEAR_BASE_URL = "/api/releaseYears";
    @MockBean
    private ReleaseYearService releaseYearService;

    @Autowired
    private MockMvc mockMvc;

    private static ReleaseYearDto buildReleaseYear(int year) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(123);
        movieDto.setRating(10);
        movieDto.setTitle("Rambo");
        movieDto.setYear(year);
        return new ReleaseYearDto(year, List.of(movieDto));
    }

    @Test
    void getAllYears() throws Exception {
        ReleaseYearDto releaseYear2010 = buildReleaseYear(2010);

        Mockito.when(releaseYearService.findAllYears()).thenReturn(List.of(releaseYear2010));

        mockMvc.perform(get(API_RELEASE_YEAR_BASE_URL)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$[0].year", is(2010)))//
                .andExpect(jsonPath("$[0].movies[0].id", is(123)))
                .andExpect(jsonPath("$[0].movies[0].title", is("Rambo")))
                .andExpect(jsonPath("$[0].movies[0].rating", is(10)));

        Mockito.verify(releaseYearService, Mockito.times(1)).findAllYears();
    }

    @Test
    void getMoviesByReleaseYear() throws Exception {
        int year = 2020;
        ReleaseYearDto releaseYear2020 = buildReleaseYear(year);

        Mockito.when(releaseYearService.findReleaseYear(year)).thenReturn(releaseYear2020);

        mockMvc.perform(get(API_RELEASE_YEAR_BASE_URL + "/{year}/movies", year)) //
                .andExpect(status().isOk()) //
                .andExpect(jsonPath("$[0].id", is(123)))
                .andExpect(jsonPath("$[0].title", is("Rambo")))
                .andExpect(jsonPath("$[0].rating", is(10)));

        Mockito.verify(releaseYearService, Mockito.times(1)).findReleaseYear(year);
    }
}