package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.repository.CommentRepository;
import com.herzog.movieapp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImplementation implements MovieService {

    private final MovieRepository movieRepository;
    private final CommentRepository commentRepository;

    public MovieServiceImplementation(MovieRepository movieRepository, CommentRepository commentRepository) {
        this.movieRepository = movieRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void saveMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setGenre(movieDto.getGenre());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setAverageRating(movieDto.getAverageRating());
        movie.setComments(commentRepository.findByMovieTitle(movieDto.getTitle()));
        movieRepository.save(movie);

    }

    @Override
    public Movie findMovieByTitle(String title) {
        return movieRepository.findByTitle(title);
    }

    @Override
    public List<MovieDto> findAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movie -> mapToMovieDto(movie))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> findMoviesByGenre(String genre) {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .filter(movie -> movie.getGenre().equals(genre))
                .map(movie -> mapToMovieDto(movie))
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> findMovieByRatingAbove(int rating) {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .filter(movie -> movie.getAverageRating() >= rating)
                .map((movie) -> mapToMovieDto(movie))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMovie(String movieTitle) {
        Movie existingMovie = movieRepository.findByTitle(movieTitle);
        movieRepository.delete(existingMovie);
    }

    private MovieDto mapToMovieDto (Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle(movie.getTitle());
        movieDto.setGenre(movie.getGenre());
        movieDto.setReleaseDate(movie.getReleaseDate());
        movieDto.setAverageRating(movie.getAverageRating());
        return movieDto;
    }
}
