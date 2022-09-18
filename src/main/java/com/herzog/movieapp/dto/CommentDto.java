package com.herzog.movieapp.dto;

import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String movieTitle;
    @NotEmpty
    private LocalDateTime dateTime;
    @NotEmpty(message = "To have your comment submitted, please share your opinion")
    private String content;
}
