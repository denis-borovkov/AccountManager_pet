package com.github.denis_borovkov.accountmanager_pet.model;

import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import org.springframework.stereotype.Component;

@Component
public class Guest {
    ConsoleUI ui = new ConsoleUI();
    private final Role.RoleType role = Role.RoleType.GUEST;

    public void showMenu() {
        ui.printSeparator();
        ui.printMenu(
                """
                        Добро пожаловать! Выберите действие:\s
                        1. Зарегистрироваться \s
                        2. Войти в учетную запись \s
                        3. Выйти из программы\s""");
        ui.printSeparator();
}

    public Role.RoleType getRole() {
        return role;
    }
}
