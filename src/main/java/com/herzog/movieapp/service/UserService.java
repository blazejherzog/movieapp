package com.herzog.movieapp.service;

import com.herzog.movieapp.dto.UserDto;
import com.herzog.movieapp.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
