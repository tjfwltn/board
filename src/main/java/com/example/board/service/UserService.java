package com.example.board.service;

import com.example.board.dto.UserRequest;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String create(UserRequest userRequest) {
        User user = new User(userRequest.getUsername());
        userRepository.save(user);
        return "User created";
    }
}
