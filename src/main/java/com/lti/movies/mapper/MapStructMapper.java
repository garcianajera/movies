package com.lti.movies.mapper;

import com.lti.movies.dto.ClientDto;
import com.lti.movies.dto.MovieDto;
import com.lti.movies.dto.MovieRatingDto;
import com.lti.movies.dto.ReleaseYearDto;
import com.lti.movies.entity.Client;
import com.lti.movies.entity.Movie;
import com.lti.movies.entity.MovieRating;
import com.lti.movies.entity.ReleaseYear;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "year", expression = "java(movie.getReleaseYear().getYear())")
    MovieDto movieToMovieDto(Movie movie);

    @Mapping(target = "releaseYear", ignore = true)
    Movie movieDtoToMovie(MovieDto movieDto);

    List<MovieDto> movieToDtoList(List<Movie> movies);

    ReleaseYearDto releaseYearToDto(ReleaseYear releaseYear);

    List<ReleaseYearDto> releaseYearToDtoList(List<ReleaseYear> releaseYears);

    @Mapping(target = "id", ignore = true)
    ReleaseYear releaseYearDtoToReleaseYear(ReleaseYearDto releaseYearDto);

    ClientDto clientToDto(Client client);

    Client clientDtoToClient(ClientDto clientDto);

    List<ClientDto> clientToDtoList(List<Client> clients);

    MovieRatingDto movieRatingToDto(MovieRating movieRating);

    List<MovieRatingDto> movieRatingToDtoList(List<MovieRating> movieRating);

}
