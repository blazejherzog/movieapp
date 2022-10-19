package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Movie;

import java.util.List;

public interface MovieService {

    void saveMovie(MovieDto movieDto);
    Movie findMovieByTitle(String title);
    List<MovieDto> findAllMovies();
    List<MovieDto> findMoviesByGenre(String genre);
    List<MovieDto> findMovieByRatingAbove(int rating);
    void deleteMovie(String movieTitle);
    void updateMovie(String movieTitle, MovieDto movieDto);
}
