package com.lti.movies.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;

@Getter
@Setter
@Entity
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String title;
    @Column(columnDefinition = "integer default 0")
    private Integer rating;
    @NotNull
    private URL url;

    private Date lastRatingDate;

    @ManyToOne
    @JoinColumn(name = "release_year_id", nullable = false)
    private ReleaseYear releaseYear;

    public Movie() {
    }

    public Movie(String title, ReleaseYear releaseYear, URL url) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.url = url;
    }

}
