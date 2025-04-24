package Command;

import Model.User;
import interfaces.Command;

public record RegisterUserCommand(User user) implements Command {

    @Override
    public void execute() {
    }
}
