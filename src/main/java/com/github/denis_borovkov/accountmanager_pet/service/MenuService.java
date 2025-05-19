package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.model.Role;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import org.springframework.stereotype.Service;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;

import java.util.logging.Logger;

@Service
public class MenuService {

    private final Logger logger = Logger.getLogger(MenuService.class.getName());

    private String username;
    private String password;

    ConsoleUI ui = new ConsoleUI();

    private final UserRepository userRepository;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final MessageService messageService;


    public MenuService(UserService userService,
                       AuthenticationService authenticationService,
                       UserRepository userRepository,
                       MessageService messageService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.userRepository = userRepository;
        this.messageService = messageService;
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
        String email = ui.readLine();
        ui.printSeparator();

        if (userService.createUser(new User())) {
            logger.info("Successfully created user");
        } else {
            logger.info("Failed to create user");
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
        //userService.getUsersKeys();
        String receiverName = ui.readLine();

        User sender = userService.getUserByName(username);
        User receiver = userService.getUserByName(receiverName);

        System.out.println("Сообщение:");
        String content = ui.readLine();
        messageService.sendMessage(sender, receiver, content);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}