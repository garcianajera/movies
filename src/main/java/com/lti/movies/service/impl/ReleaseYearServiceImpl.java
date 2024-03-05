package com.lti.movies.service.impl;

import com.lti.movies.dto.ReleaseYearDto;
import com.lti.movies.mapper.MapStructMapper;
import com.lti.movies.repository.ReleaseYearRepository;
import com.lti.movies.service.ReleaseYearService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseYearServiceImpl implements ReleaseYearService {
    private final MapStructMapper mapper;
    private final ReleaseYearRepository releaseYearRepository;

    public ReleaseYearServiceImpl(MapStructMapper mapper, ReleaseYearRepository releaseYearRepository) {
        this.mapper = mapper;
        this.releaseYearRepository = releaseYearRepository;
    }

    @Override
    public ReleaseYearDto findReleaseYear(Integer year) {
        return mapper.releaseYearToDto(releaseYearRepository.findByYear(year));
    }

    @Override
    public List<ReleaseYearDto> findAllYears() {
        return mapper
                .releaseYearToDtoList(
                        releaseYearRepository.findAll(
                                Sort.by(Sort.Direction.DESC, "year")));
    }
}
