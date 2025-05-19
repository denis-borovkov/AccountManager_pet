package com.github.denis_borovkov.accountmanager_pet.dto;

public record SignupRequestDTO(
        String username,
        String email,
        String password) {
}
