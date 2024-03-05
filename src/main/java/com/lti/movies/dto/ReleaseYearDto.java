package com.lti.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReleaseYearDto {
    private int year;
    private List<MovieDto> movies;
}
