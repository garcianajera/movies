package com.lti.movies.service;

import com.lti.movies.dto.ReleaseYearDto;

import java.util.List;

public interface ReleaseYearService {
    ReleaseYearDto findReleaseYear(Integer year);

    List<ReleaseYearDto> findAllYears();
}
