package com.github.denis_borovkov.accountmanager_pet.model;

import com.github.denis_borovkov.accountmanager_pet.abstractModel.AbstractUser;
import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import com.github.denis_borovkov.accountmanager_pet.interfaces.State;
import org.springframework.stereotype.Component;

@Component
public class Guest extends AbstractUser implements State {
    ConsoleUI ui = new ConsoleUI();
    private final Role.RoleType role = Role.RoleType.GUEST;

    @Override
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

    @Override
    public Role.RoleType getRole() {
        return role;
    }
}
