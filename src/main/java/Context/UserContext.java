package Context;

import Abstract.AbstractUser;
import interfaces.State;

public class UserContext implements State {

    private AbstractUser currentUser;

    public AbstractUser setCurrentUser(AbstractUser user) {
        return this.currentUser = user;
    }

    @Override
    public void showMenu() {
        if (currentUser != null) {
            currentUser.showMenu();
        }
    }
}
