package com.lti.movies.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class ReleaseYear {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "release_year")
    private int year;
    @OneToMany(mappedBy = "releaseYear")
    @OrderBy("rating desc")
    private Set<Movie> movies;

    public ReleaseYear(int year) {
        this.year = year;
    }

    public ReleaseYear() {

    }
}
