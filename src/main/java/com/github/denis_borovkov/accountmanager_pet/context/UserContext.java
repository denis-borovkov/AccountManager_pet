package com.github.denis_borovkov.accountmanager_pet.context;

import com.github.denis_borovkov.accountmanager_pet.abstractModel.AbstractUser;
import com.github.denis_borovkov.accountmanager_pet.interfaces.State;
import org.springframework.stereotype.Component;

@Component
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
