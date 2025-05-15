package com.github.denis_borovkov.accountmanager_pet.console;

import com.github.denis_borovkov.accountmanager_pet.context.UserContext;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.service.MenuService;
import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import org.springframework.stereotype.Component;


@Component
public class ConsoleApplication {

    private final UserContext userContext;
    private final MenuService menuService;
    private final ConsoleUI ui;

    public ConsoleApplication(UserContext userContext, MenuService menuService, ConsoleUI ui) {
        this.userContext = userContext;
        this.menuService = menuService;
        this.ui = ui;
    }

    public void run() {
        userContext.setCurrentUser(new User());
        userContext.showMenu();

        while (true) {

            int userAction = ui.readInt();
            switch (userAction) {
                case 1 -> menuService.handleRegistration();
                case 2 -> menuService.handleLogin();
                case 3 -> System.exit(0);
            }
        }
    }
}
