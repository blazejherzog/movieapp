package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.entity.Comment;
import com.herzog.movieapp.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("{movieTitle}/form/save")
    public String saveComment(@Valid @ModelAttribute("comment")CommentDto commentDto, @PathVariable String movieTitle,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("movie", commentDto);
            return "/comments";
        }

        commentService.saveComment(commentDto);
        return "redirect:/comments/{movieTitle}";
    }
}
