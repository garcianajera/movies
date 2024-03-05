package com.lti.movies.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieRatingKey implements Serializable {
    @Column(name = "movie_id")
    private Integer movieId;
    @Column(name = "client_id")
    private Integer clientId;


}
