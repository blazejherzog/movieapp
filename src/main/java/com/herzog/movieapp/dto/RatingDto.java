package com.herzog.movieapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {

    private Long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String movieTitle;
    @NotEmpty(message = "Please leave a rating from 0 to 100")
    private int rate;
}
