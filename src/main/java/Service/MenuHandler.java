package main.java.Service;

import main.java.Command.RegisterUserCommand;
import main.java.Command.RegisterUserHandler;
import main.java.Model.Role;
import main.java.Utility.ConsoleUI;
import main.java.Model.User;

import java.util.logging.Logger;

public class MenuHandler {

    private final ConsoleUI ui = new ConsoleUI();
    private final RegisterUserHandler registerUserHandler;
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final UserService userService = new UserService();
    private final MessageService messageService = new MessageService();
    private final Logger logger = Logger.getLogger(MenuHandler.class.getName());

    private Long id;
    private String username;
    private String password;
    private String email;
    private String content;
    private String role;

    public MenuHandler(RegisterUserHandler registerUserHandler) {
        this.registerUserHandler = registerUserHandler;
    }

    public void handleRegistration() {
        ui.println("Регистрация: \n");
        ui.printSeparator();
        ui.println("""
                Введите логин: \s
                ⦁ Логин не должен быть пустым\s
                ⦁ Длина логина должна быть не менее 3 символов и не более 20 \s
                ⦁ Логин должен начинаться с буквы и может содержать буквы, цифры и символ подчеркивания""");
        ui.printSeparator();
        username = ui.readLine();
        ui.printSeparator();
        ui.println("""
                Введите пароль: \s
                ⦁ Пароль не должен быть пустым \s
                ⦁ Длина пароля должна быть не менее 8 символов \s
                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");
        ui.printSeparator();
        password = ui.readLine();
        ui.printSeparator();
        ui.println("""
                Введите email:
                ⦁ Email не должен быть пустым \s
                ⦁ Должен содержать символ '@'""");
        ui.printSeparator();
        email = ui.readLine();

        RegisterUserCommand command = new RegisterUserCommand(
                new User(userService.generateId(), username, password, email, Role.RoleType.USER));
        boolean success = registerUserHandler.handle(command);

        if (success) {
            ui.println("Успешная регистрация.\n");
        } else {
            ui.println("Ошибка регистрации.\n");
        }
    }

    public void handleLogin() {
        System.out.println("Войдите: \n");

        System.out.println("Введите логин: ");
        username = ui.readLine();

        System.out.println("Введите пароль: ");
        password = ui.readLine();

        if (authenticationService.isAuthenticated(username, password)) {
            logger.info("Вы успешно вошли! \n");
        } else {
            logger.warning("Неверный логин или пароль \n");
        }
    }

    public void removeUserMenu() {
        ui.print("Введите логин:");
        username = ui.readLine();

        ui.print("Введите пароль:");
        password = ui.readLine();

        if (authenticationService.isAuthenticated(username, password))
            if (userService.removeUser(username))
                logger.info("Пользователь успешно удален. \n");
    }

    public void showMessageMenu() {
        ui.print("Введите получателя:\n");
        userService.getUsersKeys();
        String receiverName = ui.readLine();

        User sender = userService.getUserByName(username);
        User receiver = userService.getUserByName(receiverName);

        System.out.println("Сообщение:");
        content = ui.readLine();
        messageService.sendMessage(sender, receiver, content);
    }


    public void setRole(String role) {
        this.role = role;
    }
}