package com.lti.movies.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.net.URL;
import java.util.Date;

@Data
public class MovieDto {
    private Integer id;
    @NotNull
    private String title;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer rating;
    @NotNull
    @Min(1900)
    @Max(2100)
    private Integer year;
    @NotNull
    private URL url;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    private Date lastRatingDate;

}
