package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.dto.UserDto;
import com.herzog.movieapp.entity.Comment;
import com.herzog.movieapp.service.CommentService;
import com.herzog.movieapp.service.MovieService;
import com.herzog.movieapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final MovieService movieService;

    public CommentController(CommentService commentService, UserService userService, MovieService movieService) {
        this.commentService = commentService;
        this.userService = userService;
        this.movieService = movieService;
    }

    @GetMapping("/comments/{movieTitle}")
    public String comments(Model model, @PathVariable String movieTitle) {
        List<CommentDto> comments = commentService.findCommentsByMovieTitle(movieTitle);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comments/form")
    public String showCommentForm(Model model) {
        CommentDto comment = new CommentDto();
        List<UserDto> users = userService.findAllUsers();
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("comment", comment);
        model.addAttribute("movies", movies);
        model.addAttribute("users", users);
        return "comment_form";
    }

    @GetMapping("/comments/edit/{id}")
    public String showEditCommentForm(@PathVariable("id") Long id, Model model) {
        CommentDto comment = commentService.findCommentById(id);
        model.addAttribute("comment", comment);
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "comment_form";
    }

    @PostMapping("/comments/form/save")
    public String saveComment(@Valid @ModelAttribute("comment")CommentDto commentDto,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("comment", commentDto);
            return "/comments";
        }

        commentService.saveComment(commentDto);
        return "redirect:/movies";
    }

    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id, Model model) {
        commentService.deleteComment(id);
        return "redirect:/movies";
    }
}
