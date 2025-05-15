package com.github.denis_borovkov.accountmanager_pet.context;

import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserContext {

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void showMenu() {
        if (currentUser != null) {
            currentUser.showMenu();
        }
    }
}
