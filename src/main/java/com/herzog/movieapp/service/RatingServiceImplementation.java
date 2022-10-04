package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.RatingDto;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.entity.Rating;
import com.herzog.movieapp.entity.User;
import com.herzog.movieapp.repository.MovieRepository;
import com.herzog.movieapp.repository.RatingRepository;
import com.herzog.movieapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatingServiceImplementation implements RatingService{

    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public RatingServiceImplementation(RatingRepository ratingRepository, ModelMapper modelMapper, UserRepository userRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveRating(RatingDto ratingDto) {
        Rating rating = new Rating();
        User existingUser = userRepository.findByName(ratingDto.getUserName());
        Movie existingMovie = movieRepository.findByTitle(ratingDto.getMovieTitle());
        rating.setUser(existingUser);
        rating.setMovie(existingMovie);
        rating.setRate(ratingDto.getRate());
        ratingRepository.save(rating);

    }

    @Override
    public List<RatingDto> findAllRatingsByMovieTitle(String title) {
        List<Rating> ratings = ratingRepository.findByMovieTitle(title);
        return ratings.stream()
                .map(rating -> modelMapper.map(rating, RatingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int countAverageRatingByMovieTitle(String title) {
        List<Rating> ratings = ratingRepository.findByMovieTitle(title);
        List<Integer> values = ratings.stream().map(rating -> rating.getRate()).collect(Collectors.toList());
        int sum = 0;
        int avg = 0;
        for (int value : values) {
            sum += value;
        }
        avg = sum / values.size();
        return avg;
    }

    @Override
    public void deleteRating(String movieTitle) {
        List<Rating> ratingsByTitle = ratingRepository.findByMovieTitle(movieTitle);
        for (Rating rating : ratingsByTitle) {
            if (rating.getMovie().getTitle().equals(movieTitle)) {
                ratingRepository.delete(rating);
            }
        }

    }
}
