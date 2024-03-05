package com.lti.movies.service.impl;

import com.lti.movies.dto.ReleaseYearDto;
import com.lti.movies.entity.ReleaseYear;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.mapper.MapStructMapperImpl;
import com.lti.movies.repository.ReleaseYearRepository;
import com.lti.movies.service.ReleaseYearService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {MapStructMapper.class, MapStructMapperImpl.class})
@RunWith(SpringRunner.class)
class ReleaseYearServiceImplTest {
    @Autowired
    private MapStructMapper mapper;

    @Test
    void findReleaseYear() {

        ReleaseYearRepository releaseYearRepository = Mockito.mock(ReleaseYearRepository.class);
        int year = 2020;

        Mockito.when(releaseYearRepository.findByYear(year)).thenReturn(new ReleaseYear(year));
        ReleaseYearService releaseYearService = new ReleaseYearServiceImpl(mapper, releaseYearRepository);

        ReleaseYearDto releaseYearDto = releaseYearService.findReleaseYear(year);
        assertEquals(2020, releaseYearDto.getYear());
    }

    @Test
    void findAllYears() {
        ReleaseYearRepository releaseYearRepository = Mockito.mock(ReleaseYearRepository.class);
        int year = 2020;

        Mockito.when(releaseYearRepository.findAll(Sort.by(Sort.Direction.DESC, "year"))).thenReturn(List.of(new ReleaseYear(year)));
        ReleaseYearService releaseYearService = new ReleaseYearServiceImpl(mapper, releaseYearRepository);

        List<ReleaseYearDto> releaseYearDtoList = releaseYearService.findAllYears();
        assertEquals(2020, releaseYearDtoList.get(0).getYear());
    }
}