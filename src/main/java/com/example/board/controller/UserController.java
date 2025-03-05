package com.example.board.controller;

import com.example.board.dto.UserRequest;
import com.example.board.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }
}
