package com.github.denis_borovkov.accountmanager_pet.controllers;

import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import com.github.denis_borovkov.accountmanager_pet.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("{username}")
    public User getUser(@PathVariable String username) {
        return userRepository.getUserByUsername(username);
    }

    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

}
