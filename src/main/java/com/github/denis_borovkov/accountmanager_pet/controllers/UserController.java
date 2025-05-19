package com.github.denis_borovkov.accountmanager_pet.controllers;

import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{username}")
    public User getUser(@PathVariable String username) {
        return userRepository.getUserByUsername(username);
    }
}
