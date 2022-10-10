package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.entity.Comment;
import com.herzog.movieapp.entity.Movie;
import com.herzog.movieapp.entity.User;
import com.herzog.movieapp.repository.CommentRepository;
import com.herzog.movieapp.repository.MovieRepository;
import com.herzog.movieapp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImplementation implements CommentService{

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public CommentServiceImplementation(CommentRepository commentRepository, ModelMapper modelMapper, UserRepository userRepository, MovieRepository movieRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveComment(CommentDto commentDto) {
        Comment comment = new Comment();
        User existingUser = userRepository.findByEmail(commentDto.getUserEmail());
        Movie existingMovie = movieRepository.findByTitle(commentDto.getMovieTitle());
        comment.setUser(existingUser);
        comment.setMovie(existingMovie);
        comment.setDateTime(commentDto.getDateTime());
        comment.setContent(commentDto.getContent());
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
    public Comment findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).get();
        return comment;
    }

    @Override
    public void deleteComment(Long commentId) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            commentRepository.delete(existingComment.get());
        }
    }
}
