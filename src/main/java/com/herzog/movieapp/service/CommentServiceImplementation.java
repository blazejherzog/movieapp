package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.entity.Comment;
import com.herzog.movieapp.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService{

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImplementation(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setUser(commentDto.getUserName().equals(comment.getUser().getName()) ? comment.getUser() : null);
        comment.setMovie(commentDto.getMovieTitle().equals(comment.getMovie().getTitle()) ? comment.getMovie() : null);
        comment.setDateTime(commentDto.getDateTime());
        comment.setContent(comment.getContent());
        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> findCommentsByMovieTitle(String movieTitle) {
        List<Comment> comments = commentRepository.findByMovieTitle(movieTitle);
        return comments.stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            commentRepository.delete(existingComment.get());
        }
    }
}
