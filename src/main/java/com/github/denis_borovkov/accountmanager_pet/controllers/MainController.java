package com.github.denis_borovkov.accountmanager_pet.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/secured")
public class MainController {

    @GetMapping("/user")
    public String userAccess(Principal principal) {
        return principal == null ? null : principal.getName();
    }
}
