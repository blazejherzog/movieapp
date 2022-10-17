package com.herzog.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private Long id;

    private String userEmail;

    private String movieTitle;

    @NotNull(message = "Please leave a rating from 1 to 100")
    @Min(1)
    @Max(100)
    private int rate;
}
