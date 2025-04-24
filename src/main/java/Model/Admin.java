package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import Abstract.AbstractUser;
import Service.NotificationService;
import Utility.ConsoleUI;
import interfaces.State;

public class Admin extends AbstractUser implements State {

    public Admin() {}

    ConsoleUI ui;
    NotificationService notificationService;


    public Admin(Long id, String username, String password, String email, Role.RoleType role) {
        super(id, username, password, email, role);
        setPassword(password);
        new NotificationService();
    }


    @JsonIgnore
    public NotificationService getNotificationService() {
        if (notificationService == null)
            this.notificationService = new NotificationService();
        return notificationService;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public void showMenu() {
        ui.printMenu("Добро пожаловать, " + getUsername() + "! Выберите действие:\n" +
                "1. Просмотреть информацию о пользователях\n" +
                "2. Мои сообщения\n" +
                "3. Мои уведомления\n" +
                "4. Изменить пароль\n" +
                "5. Изменить email\n" +
                "6. Выйти из учётной записи\n");
    }

    @Override
    public Role.RoleType getRole() {
        return super.getRole();
    }

    @Override
    public Long getId() {
        return super.getId();
    }
}
