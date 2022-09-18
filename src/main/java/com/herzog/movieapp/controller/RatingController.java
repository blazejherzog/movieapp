package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.RatingDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.service.MovieService;
import com.herzog.movieapp.service.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;
    private final MovieService movieService;

    public RatingController(RatingService ratingService, MovieService movieService) {
        this.ratingService = ratingService;
        this.movieService = movieService;
    }

    @PostMapping("{movieTitle}/form/save")
    public String saveRating(@Valid @ModelAttribute("rating") RatingDto ratingDto, @PathVariable String movieTitle,
                             BindingResult result, Model model) {
        Movie existingMovie = movieService.findMovieByTitle(movieTitle);
        if (result.hasErrors()) {
            model.addAttribute("rating", ratingDto);
            return "/ratings";
        }
        ratingService.saveRating(ratingDto);
        existingMovie.setAverageRating(ratingService.countAverageRatingByMovieTitle(movieTitle));
        return "redirect:/ratings/{movieTitle}";
    }
}
