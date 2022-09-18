package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/form/save")
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
}
