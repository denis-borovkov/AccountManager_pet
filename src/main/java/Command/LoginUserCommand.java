package Command;

import Service.AuthenticationService;
import interfaces.Command;

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
