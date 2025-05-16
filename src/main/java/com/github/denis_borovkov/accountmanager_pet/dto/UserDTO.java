package com.github.denis_borovkov.accountmanager_pet.dto;

import com.github.denis_borovkov.accountmanager_pet.model.Role;

public record UserDTO(String username, String email, Role userRole) {}
