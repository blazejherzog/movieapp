package com.herzog.movieapp.repository;

import com.herzog.movieapp.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByMovieTitle(String movieTitle);
}
