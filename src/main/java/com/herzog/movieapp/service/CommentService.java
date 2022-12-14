package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Comment;

import java.util.List;

public interface CommentService {

    void saveComment(CommentDto commentDto);
    List<CommentDto> findCommentsByMovieTitle(String movieTitle);
    CommentDto findCommentById(Long id);
    void deleteComment(Long commentId);
    void updateComment(Long commentId, CommentDto commentDto);

}
