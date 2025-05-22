package com.github.denis_borovkov.accountmanager_pet.controllers;

import com.github.denis_borovkov.accountmanager_pet.dto.UserDTO;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import com.github.denis_borovkov.accountmanager_pet.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("users")
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userRepository.getUserByUsername(username);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
