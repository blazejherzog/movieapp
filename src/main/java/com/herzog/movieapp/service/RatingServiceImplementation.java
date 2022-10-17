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
import java.util.Optional;
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
        User existingUser = userRepository.findByEmail(ratingDto.getUserEmail());
        Movie existingMovie = movieRepository.findByTitle(ratingDto.getMovieTitle());
        rating.setUser(existingUser);
        rating.setMovie(existingMovie);
        rating.setRate(ratingDto.getRate());
        ratingRepository.save(rating);
        existingMovie.setAverageRating(countAverageRatingByMovieTitle(existingMovie.getTitle()));
        movieRepository.flush();
    }

    @Override
    public List<RatingDto> findAllRatingsByMovieTitle(String movieTitle) {
        List<Rating> ratings = ratingRepository.findByMovieTitle(movieTitle);
        return ratings.stream()
                .map(rating -> modelMapper.map(rating, RatingDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public int countAverageRatingByMovieTitle(String movieTitle) {
        List<Rating> ratings = ratingRepository.findByMovieTitle(movieTitle);
        List<Integer> values = ratings.stream().map(rating -> rating.getRate()).collect(Collectors.toList());
        if (values.size() == 0) {
            return 0;
        } else {
            int sum = 0;
            int avg = 0;
            for (int value : values) {
                sum += value;
            }
            avg = sum / values.size();
            return avg;
        }
    }

    @Override
    public void deleteRating(Long id) {
        Optional<Rating> existingRating = ratingRepository.findById(id);
        if (existingRating.isPresent()) {
            ratingRepository.delete(existingRating.get());
        }
        existingRating.get().getMovie().setAverageRating(countAverageRatingByMovieTitle(existingRating.get().getMovie().getTitle()));
        movieRepository.flush();
    }
}
