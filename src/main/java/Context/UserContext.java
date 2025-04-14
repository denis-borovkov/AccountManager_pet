package main.java.Context;

import main.java.Abstract.AbstractUser;
import main.java.interfaces.State;

public class UserContext implements State {

    private AbstractUser currentUser;

    public AbstractUser setCurrentUser(AbstractUser user) {
        return this.currentUser = user;
    }

    public void showMenu() {
        if (currentUser != null) {
            currentUser.showMenu();
        }
    }
}
