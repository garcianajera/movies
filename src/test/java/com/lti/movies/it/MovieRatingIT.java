package com.lti.movies.it;


import com.lti.movies.dto.ClientDto;
import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.MovieRatingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieRatingIT {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void movieRating_clientVoteUp() throws Exception {
        String baseUrl = "http://localhost:" + port + "/api";

        //create a user
        ClientDto clientDto = new ClientDto();
        clientDto.setUserName("userTest");
        clientDto = restTemplate.postForObject(baseUrl + "/clients", clientDto, ClientDto.class);
        //create a movie
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie Test");
        movieDto.setUrl(new URL("http://test.net"));
        movieDto.setYear(2020);
        movieDto = restTemplate.postForObject(baseUrl + "/movies", movieDto, MovieDto.class);
        //vote up
        MovieRatingDto movieRatingDto =
                restTemplate.postForObject(baseUrl + "/movieRatings/movie/{movieId}/client/{clientId}/voteUp",
                        null, MovieRatingDto.class, movieDto.getId(), clientDto.getId());

        assertEquals(1, movieRatingDto.getClientRating().intValue());
        // get movie rating should be 1
        MovieDto movieWithRating = restTemplate.getForObject(baseUrl + "/movies/{movieId}",
                MovieDto.class, movieDto.getId());
        assertNotNull(movieWithRating);
        assertEquals(1, movieWithRating.getRating().intValue());
    }

    @Test
    void movieRating_clientVoteDown() throws Exception {
        String baseUrl = "http://localhost:" + port + "/api";

        //create a user
        ClientDto clientDto = new ClientDto();
        clientDto.setUserName("userTest");
        clientDto = restTemplate.postForObject(baseUrl + "/clients", clientDto, ClientDto.class);
        //create a movie
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle("Movie Test");
        movieDto.setUrl(new URL("http://test.net"));
        movieDto.setYear(2020);
        movieDto = restTemplate.postForObject(baseUrl + "/movies", movieDto, MovieDto.class);
        //vote up
        MovieRatingDto movieRatingDto =
                restTemplate.postForObject(baseUrl + "/movieRatings/movie/{movieId}/client/{clientId}/voteDown",
                        null, MovieRatingDto.class, movieDto.getId(), clientDto.getId());

        assertEquals(-1, movieRatingDto.getClientRating().intValue());
        // get movie rating should be 1

        MovieDto movieWithRating = restTemplate.getForObject(baseUrl + "/movies/{movieId}",
                MovieDto.class, movieDto.getId());
        assertNotNull(movieWithRating);
        assertEquals(-1, movieWithRating.getRating().intValue());
    }
}
