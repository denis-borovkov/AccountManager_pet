package com.github.denis_borovkov.accountmanager_pet.command;

import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.interfaces.Command;
import org.springframework.stereotype.Component;

@Component
public record RegisterUserCommand(User user) implements Command {

    @Override
    public void execute() {
    }
}
