package com.lti.movies.controller;

import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.ReleaseYearDto;
import com.lti.movies.service.ReleaseYearService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/releaseYears")
@Log4j2
public class ReleaseYearController {
    private final ReleaseYearService releaseYearService;

    @Autowired
    public ReleaseYearController(ReleaseYearService releaseYearService) {
        this.releaseYearService = releaseYearService;
    }

    @GetMapping
    public List<ReleaseYearDto> getAllYears() {
        return releaseYearService.findAllYears();
    }

    @GetMapping("/{year}/movies")
    public List<MovieDto> getMoviesByReleaseYear(@PathVariable Integer year) {
        log.debug(" Find movies by year " + year);
        ReleaseYearDto releaseYear = releaseYearService.findReleaseYear(year);
        if (releaseYear != null) {
            return releaseYear.getMovies();
        }
        return Collections.emptyList();
    }
}
