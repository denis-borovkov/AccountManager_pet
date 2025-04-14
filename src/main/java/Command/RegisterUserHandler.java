package main.java.Command;

import main.java.Service.UserService;
import main.java.Model.User;

public class RegisterUserHandler {

    private final UserService userService;

    public RegisterUserHandler(UserService userService) {
        this.userService = userService;
    }

    public boolean handle(RegisterUserCommand command) {
        User user = command.user();
        return userService.createUser(user);
    }
}
