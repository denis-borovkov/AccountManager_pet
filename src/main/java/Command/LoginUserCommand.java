package main.java.Command;

import main.java.Service.AuthenticationService;
import main.java.interfaces.Command;

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
