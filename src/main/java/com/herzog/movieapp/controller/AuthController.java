package com.herzog.movieapp.controller;

import com.herzog.movieapp.dto.CommentDto;
import com.herzog.movieapp.dto.MovieDto;
import com.herzog.movieapp.dto.UserDto;
import com.herzog.movieapp.entity.Comment;
import com.herzog.movieapp.entity.User;
import com.herzog.movieapp.service.CommentService;
import com.herzog.movieapp.service.MovieService;
import com.herzog.movieapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AuthController {

    private UserService userService;
    private MovieService movieService;
    private CommentService commentService;

    public AuthController(UserService userService, MovieService movieService, CommentService commentService) {
        this.userService = userService;
        this.movieService = movieService;
        this.commentService = commentService;
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/movies/form")
    public String showMovieForm(Model model) {
        MovieDto movie = new MovieDto();
        model.addAttribute("movie", movie);
        return "movieform";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/movies")
    public String movies(Model model) {
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("movies", movies);
        return "movies";
    }

    @GetMapping("/comments/{movieTitle}")
    public String comments(Model model, @PathVariable String movieTitle) {
        List<CommentDto> comments = commentService.findCommentsByMovieTitle(movieTitle);
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/comments/{movieTitle}/form")
    public String showCommentForm(Model model, @PathVariable String movieTitle) {
        CommentDto comment = new CommentDto();
        List<UserDto> users = userService.findAllUsers();
        List<MovieDto> movies = movieService.findAllMovies();
        model.addAttribute("comment", comment);
        model.addAttribute("movies", movies);
        model.addAttribute("users", users);
        return "commentform";
    }
}
