package com.lti.movies.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MovieRatingDto {
    private MovieDto movie;
    private ClientDto client;
    private Integer clientRating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    private Date ratingDate;

}
