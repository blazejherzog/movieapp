package com.herzog.movieapp.repository;

import com.herzog.movieapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMovieTitle(String title);
}
