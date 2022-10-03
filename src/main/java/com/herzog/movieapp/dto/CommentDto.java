package com.herzog.movieapp.dto;

import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private User user;

    private Movie movie;

    private LocalDateTime dateTime = LocalDateTime.now();

    @NotEmpty(message = "To have your comment submitted, please share your opinion")
    private String content;
}
