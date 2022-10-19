package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.dto.RatingDto;
import com.herzog.movieapp.dto.UserDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.service.MovieService;
import com.herzog.movieapp.service.RatingService;
import com.herzog.movieapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RatingController {

    private final RatingService ratingService;
    private final MovieService movieService;
    private final UserService userService;

    public RatingController(RatingService ratingService, MovieService movieService, UserService userService) {
        this.ratingService = ratingService;
        this.movieService = movieService;
        this.userService = userService;
    }

    @GetMapping("/ratings/form")
    public String showRatingForm(Model model) {
        RatingDto rating = new RatingDto();
        List<UserDto> users = userService.findAllUsers();
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("rating", rating);
        model.addAttribute("movies", movies);
        model.addAttribute("users", users);
        return "rating_form";
    }

    @PostMapping("/ratings/form/save")
    public String saveRating(@Valid @ModelAttribute("rating") RatingDto ratingDto,
                             BindingResult result, Model model) {
        Movie existingMovie = movieService.findMovieByTitle(ratingDto.getMovieTitle());
        if (result.hasErrors()) {
            model.addAttribute("rating", ratingDto);
            return "/ratings";
        }
        ratingService.saveRating(ratingDto);
        existingMovie.setAverageRating(ratingService.countAverageRatingByMovieTitle(ratingDto.getMovieTitle()));
        return "redirect:/movies";
    }

    @GetMapping("/ratings/{movieTitle}")
    public String ratings(@PathVariable String movieTitle, Model model) {
        List<RatingDto> ratings = ratingService.findAllRatingsByMovieTitle(movieTitle);
        model.addAttribute("ratings", ratings);
        return "ratings";
    }

    @GetMapping("/ratings/delete/{id}")
    public String deleteRating(@PathVariable("id") Long id, Model model) {
        ratingService.deleteRating(id);
        return "redirect:/movies";
    }
}
