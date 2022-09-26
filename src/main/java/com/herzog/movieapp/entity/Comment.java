package com.herzog.movieapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_name")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_title")
    private Movie movie;

    @Column(nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    @Column(nullable = false)
    private String content;
}
