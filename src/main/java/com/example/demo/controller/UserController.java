package com.example.demo.controller;


import com.example.demo.openapi.api.UserApi;
import com.example.demo.openapi.model.UserDto;
import com.example.demo.openapi.model.UserResponseDto;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserApi {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserResponseDto> getAllUsers() {
        List<UserDto> userDtos = userService.findAllUsers();
        return ResponseEntity.ok(new UserResponseDto(userDtos));
    }
}
