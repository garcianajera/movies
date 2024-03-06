package com.lti.movies.repository;

import com.lti.movies.entity.ReleaseYear;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext
@ActiveProfiles("test")
class ReleaseYearRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReleaseYearRepository releaseYearRepository;
    @Test
    void findByYear() {
        int year = 2020;

        ReleaseYear releaseYear = new ReleaseYear();
        releaseYear.setYear(year);

        Assert.assertNull(releaseYearRepository.findByYear(year));

        entityManager.persist(releaseYear);

        ReleaseYear releaseYearFromDb = releaseYearRepository.findByYear(year);
        Assert.assertEquals(year,releaseYearFromDb.getYear());
    }
}