package Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import Abstract.AbstractUser;
import Service.NotificationService;
import Utility.ConsoleUI;
import interfaces.State;

public class User extends AbstractUser implements State {

    ConsoleUI ui;
    NotificationService notificationService;
    public User() {}

    public User(Long id, String username, String password, String email, Role.RoleType role) {
        super(id, username, password, email, role);
        setPassword(password);
        new NotificationService();
    }

    @Override
    public void showMenu() {
        ui.printMenu("Добро пожаловать, " + getUsername() + "! Выберите действие:\n" +
                "1. Просмотреть личную информацию\n" +
                "2. Мои сообщения\n" +
                "3. Мои уведомления\n" +
                "4. Изменить пароль\n" +
                "5. Изменить email\n" +
                "6. Выйти из учётной записи\n");
    }

    @JsonIgnore
    public NotificationService getNotificationService() {
        if (notificationService == null)
            this.notificationService = new NotificationService();
        return notificationService;
    }

    @Override
    @JsonIgnore
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }


    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public Role.RoleType getRole() {
        return super.getRole();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
    }

    @Override
    public Long getId() {
        return super.getId();
    }
}
