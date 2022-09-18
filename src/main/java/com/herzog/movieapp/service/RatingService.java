package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.RatingDto;
import com.herzog.movieapp.entity.Rating;

import java.util.List;

public interface RatingService {

    void saveRating(RatingDto ratingDto);
    List<RatingDto> findAllRatingsByMovieTitle(String title);
    int countAverageRatingByMovieTitle(String title);
    void deleteRating(String movieTitle);
}
