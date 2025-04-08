package main.java.Service;

import main.java.Command.RegisterUserCommand;
import main.java.Command.RegisterUserHandler;
import main.java.Utility.ConsoleUI;
import main.java.Utility.User;
import main.java.Utility.UserRole;

import java.util.logging.Logger;

public class MenuHandler {

    private final ConsoleUI ui = new ConsoleUI();
    private final RegisterUserHandler registerUserHandler;
    private final AuthenticationService authenticationService = new AuthenticationService();
    private final UserService userService = new UserService();
    private final MessageService messageService = new MessageService();
    private final Logger logger = Logger.getLogger(MenuHandler.class.getName());

    private String username;
    private String password;
    private String email;
    private String content;

    public MenuHandler(RegisterUserHandler registerUserHandler) {
        this.registerUserHandler = registerUserHandler;
    }

    public void showWelcomeMenu() {
        ui.printMenu("""
                Добро пожаловать! Выберите действие:\s
                1. Зарегистрироваться\s
                2. Войти в учетную запись\s
                3. Выйти из программы\s
                """);
    }

    public void handleRegistration() {
        ui.println("Регистрация: \n");

        ui.println("""
                Введите логин: \s
                ⦁ Логин не должен быть пустым\s
                ⦁ Длина логина должна быть не менее 3 символов и не более 20 \s
                ⦁ Логин должен начинаться с буквы и может содержать буквы, цифры и символ подчеркивания \n""");

        username = ui.readLine(username);

        ui.println("""
                Введите пароль: \s
                ⦁ Пароль не должен быть пустым \s
                ⦁ Длина пароля должна быть не менее 8 символов \s
                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *) \n""");

        password = ui.readLine(password);

        ui.println("""
                Введите email:
                ⦁ Email не должен быть пустым \s
                ⦁ Должен содержать символ '@' \n""");

        email = ui.readLine(email);
        RegisterUserCommand command = new RegisterUserCommand(new User(username, password, email, UserRole.USER));
        boolean success = registerUserHandler.handle(command);

        if (success) {
            ui.println("Успешная регистрация.");
        } else {
            ui.println("Ошибка регистрации.");
        }
    }

    public void handleLogin() {
        System.out.println("Войдите: \n");

        System.out.println("Введите логин: ");
        username = ui.readLine(username);

        System.out.println("Введите пароль: ");
        password = ui.readLine(password);

        if (authenticationService.isAuthenticated(username, password)) {
            logger.info("Вы успешно вошли! \n");
        } else {
            logger.warning("Неверный логин или пароль \n");
        }
    }

    public void showAdminMenu() {
        ui.printMenu("Добро пожаловать, " + username + "! Выберите действие:\n" +
                "1. Просмотреть информацию о пользователях\n" +
                "2. Мои сообщения\n" +
                "3. Мои уведомления\n" +
                "4. Изменить пароль\n" +
                "5. Изменить email\n" +
                "6. Выйти из учётной записи\n");
    }

    public void removeUserMenu() {
        ui.print("Введите логин:");
        username = ui.readLine(username);

        ui.print("Введите пароль:");
        password = ui.readLine(password);

        if (authenticationService.isAuthenticated(username, password))
            if (userService.removeUser(username))
                logger.info("Пользователь успешно удален. \n");
    }

    public void showMessageMenu() {
        ui.print("Введите получателя:\n");
        userService.getUsersKeys();
        String receiverName = ui.readLine(username);

        User sender = userService.getUserByName(username);
        User receiver = userService.getUserByName(receiverName);

        System.out.println("Сообщение:");
        content = ui.readLine(content);
        messageService.sendMessage(sender, receiver, content);
    }
}