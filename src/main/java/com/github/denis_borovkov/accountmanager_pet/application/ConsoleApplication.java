package com.github.denis_borovkov.accountmanager_pet.application;

import com.github.denis_borovkov.accountmanager_pet.context.UserContext;
import com.github.denis_borovkov.accountmanager_pet.model.Guest;
import com.github.denis_borovkov.accountmanager_pet.service.MenuHandler;
import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ConsoleApplication {

    private final UserContext userContext;
    private final MenuHandler menuHandler;
    private final ConsoleUI ui;

    @Autowired
    public ConsoleApplication(UserContext userContext, MenuHandler menuHandler, ConsoleUI ui) {
        this.userContext = userContext;
        this.menuHandler = menuHandler;
        this.ui = ui;
    }

    public void run() {
        userContext.setCurrentUser(new Guest());
        userContext.showMenu();

        while (true) {

            int userAction = ui.readInt();
            switch (userAction) {
                case 1 -> menuHandler.handleRegistration();
                case 2 -> menuHandler.handleLogin();
                case 3 -> System.exit(0);
            }
        }
    }
}
