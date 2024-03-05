package com.lti.movies.repository;

import com.lti.movies.entity.ReleaseYear;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseYearRepository extends JpaRepository<ReleaseYear, Integer> {
    ReleaseYear findByYear(Integer year);
}
