package main.java.Command;

import main.java.Model.User;
import main.java.interfaces.Command;

public record RegisterUserCommand(User user) implements Command {

    @Override
    public void execute() {
    }
}
