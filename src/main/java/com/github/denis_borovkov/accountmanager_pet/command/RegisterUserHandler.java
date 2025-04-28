package com.github.denis_borovkov.accountmanager_pet.command;

import com.github.denis_borovkov.accountmanager_pet.service.UserService;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserHandler {

    @Autowired
    private UserService userService;

    public RegisterUserHandler() {
    }

    public boolean handle(RegisterUserCommand command) {
        User user = command.user();
        return userService.createUser(user);
    }
}
