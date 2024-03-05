package com.lti.movies;

import com.lti.movies.entity.Client;
import com.lti.movies.entity.Movie;
import com.lti.movies.entity.ReleaseYear;
import com.lti.movies.repository.ClientRepository;
import com.lti.movies.repository.MovieRepository;
import com.lti.movies.repository.ReleaseYearRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Configuration
@Log4j2
public class LoadDatabase {
    @Bean
    CommandLineRunner initDatabase(MovieRepository movieRepository, ReleaseYearRepository releaseYearRepository, ClientRepository clientRepository) {

        return args -> {
            ReleaseYear year2018 = releaseYearRepository.save(new ReleaseYear(2018));
            log.info("Preloading Release Year 2018" + year2018);
            ReleaseYear year2020 = releaseYearRepository.save(new ReleaseYear(2020));
            log.info("Preloading Release Year 2020" + year2020);

            log.info("Preloading Movie 1" + movieRepository.save(new Movie("Minions", year2018, new URL("http://movie1"))));
            log.info("Preloading Movie 2" + movieRepository.save(new Movie("Cars", year2020, new URL("http://movie2"))));

            log.info("Preloading Client " + clientRepository.save(new Client(1, "John")));
            log.info("Preloading Client " + clientRepository.save(new Client(2, "Mary")));

        };
    }
}
