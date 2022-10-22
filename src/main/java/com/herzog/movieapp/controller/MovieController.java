package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.repository.MovieRepository;
import com.herzog.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;

    public MovieController(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies/form")
    public String showMovieForm(Model model) {
        MovieDto movie = new MovieDto();
        model.addAttribute("movie", movie);
        return "movie_form";
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @PostMapping("/movies/form/save")
    public String saveMovie(@Valid @ModelAttribute("movie")MovieDto movieDto,
                            BindingResult result, Model model) {
        Movie existingMovie = movieService.findMovieByTitle(movieDto.getTitle());
        if (existingMovie != null && existingMovie.getTitle() != null && !existingMovie.getTitle().isEmpty()) {
            result.rejectValue("title", null, "The movie is already saved");
        }
        if (result.hasErrors()) {
            model.addAttribute("movie", movieDto);
            return "/movies";
        }
        movieService.saveMovie(movieDto);
        return "redirect:/movies";
    }

    @GetMapping("/movies/delete/{movieTitle}")
    public String deleteMovie(@PathVariable("movieTitle") String movieTitle, Model model) {
        movieService.deleteMovie(movieTitle);
        return "redirect:/movies";
    }
}
