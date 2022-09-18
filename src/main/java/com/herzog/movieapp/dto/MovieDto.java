package com.herzog.movieapp.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String genre;

    private int releaseDate;

    @Value("${moviedto.averagerating:0}")
    private int averageRating;

}
