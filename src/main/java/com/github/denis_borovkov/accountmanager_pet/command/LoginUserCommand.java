package com.github.denis_borovkov.accountmanager_pet.command;

import com.github.denis_borovkov.accountmanager_pet.service.AuthenticationService;
import com.github.denis_borovkov.accountmanager_pet.interfaces.Command;
import org.springframework.stereotype.Component;

@Component
public class LoginUserCommand implements Command {

    private final AuthenticationService authenticationService;

    public LoginUserCommand(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void execute() {
        authenticationService.getUserToken();
    }


    
}
