package main.java.Service;

import main.java.Command.RegisterUserHandler;
import main.java.Utility.ConsoleUI;
import main.java.Utility.Message;
import main.java.Utility.User;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountService {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        RegisterUserHandler registerUserHandler = new RegisterUserHandler(new UserService());
        MenuHandler menuHandler = new MenuHandler(registerUserHandler);
        ConsoleUI ui = new ConsoleUI();
        User user = new User();
        AuthenticationService authenticationService = new AuthenticationService();
        UserService userService = new UserService();
        MessageService messageService = new MessageService();
        Logger logger = Logger.getLogger(AccountService.class.getName());

        int userAction;

        do {
            menuHandler.showWelcomeMenu();
            userAction = ui.readInt(String.valueOf(userAction));
            switch (userAction) {
                case 1 -> menuHandler.handleRegistration();
                case 2 -> menuHandler.handleLogin();
                    }
                    if (userService.isAdmin(user)) {
                        do {
                            menuHandler.showAdminMenu();
                            userAction = ui.readInt(String.valueOf(userAction));
                            switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                case 1:
                                    do {
                                        userService.listUsers();
                                        System.out.println("""
                                                Выберите действие:\s
                                                1. Изменение пользовательских прав\s
                                                2. Удалить пользователя\s
                                                3. Вернуться назад\s
                                                """);
                                        switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                            case 1:
                                                System.out.println("Введите пароль администратора:");
                                                String rootPassword = scanner.nextLine();
                                                System.out.println("Введите имя пользователя:");
                                                username = scanner.nextLine();

                                                System.out.println("""
                                                        1. Предоставить права администратора\s
                                                        2. Предоставить права пользователя\s
                                                        3. Вернуться назад.\s
                                                        """);
                                                userAction = Integer.parseInt(scanner.nextLine());
                                                if (userAction == 1) {
                                                    userService.grantAdminRights(username, rootPassword);
                                                    userAction = 5;
                                                    break;
                                                } else if (userAction == 2) {
                                                    userService.grantUserRights(username, rootPassword);
                                                    userAction = 5;
                                                    break;
                                                } else if (userAction == 3) {
                                                break;
                                            }
                                            case 2:
                                                System.out.println("Введите логин:");
                                                username = scanner.nextLine();
                                                System.out.println();

                                                System.out.println("Введите пароль:");
                                                password = scanner.nextLine();
                                                if (authenticationService.isAuthenticated(username, password))
                                                    if (userService.removeUser(username))
                                                        logger.info("Пользователь успешно удален. \n");
                                                break;
                                            case 3:
                                                break;
                                        }
                                    } while (userAction != 3);
                                    break;
                                case 2:
                                    System.out.println("Последние сообщения:\n");
                                    List<Message> messages = messageService.getMessage(username);
                                    if (messages.isEmpty()) {
                                        System.out.println("Нет сообщений для пользователя: " + username);
                                    } else {
                                        for (Message msg : messages) {
                                            System.out.println(msg);
                                        }
                                    }

                                    System.out.println("""
                                            1. Отправить сообщение
                                            2. Веруться назад
                                            """);

                                    userAction = Integer.parseInt(scanner.nextLine());
                                    if (userAction == 1) {
                                        System.out.println("Введите получателя:\n");
                                        userService.getUsersKeys();
                                        String receiverName = scanner.nextLine();

                                        User sender = userService.getUserByName(username);
                                        User receiver = userService.getUserByName(receiverName);

                                        System.out.println("Сообщение:");
                                        String content = scanner.nextLine();
                                        messageService.sendMessage(sender, receiver, content);
                                        break;
                                    } else if (userAction == 2) {
                                        break;
                                    }
                                case 3:
                                    System.out.println("Последние уведомления: " + user.getNotificationService()
                                            .notificationsCounter(username));
                                    user.getNotificationService().showNotifications(username);
                                    break;
                                case 4:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();

                                    System.out.println("Введите старый пароль:");
                                    String oldPassword = scanner.nextLine();

                                    if (authenticationService.isAuthenticated(username, oldPassword))
                                        System.out.println("""
                                                Введите новый пароль: \s
                                                ⦁ Пароль не должен быть пустым \s
                                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                    String newPassword = scanner.nextLine();
                                    if (userService.updatePassword(username, newPassword))
                                        logger.info("Пароль был успешно изменен. \n");
                                    break;
                                case 5:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();
                                    System.out.println();

                                    if (userService.checkUsername(username))
                                        System.out.println(("""
                                                Введите новый email:
                                                ⦁ Email не должен быть пустым \s
                                                ⦁ Должен содержать символ '@'"""));

                                    String newEmail = scanner.nextLine();
                                    if (userService.updateEmail(username, newEmail))
                                        logger.info("Email был успешно изменен. \n");
                                    break;
                                case 6:
                                    break;
                            }
                        } while (authenticationService.isAuthenticated(username, password) && userAction != 6);
                    } else {
                        do {
                            System.out.println("Добро пожаловать, " + username + "! Выберите действие:\n" +
                                    "1. Просмотреть личную информацию\n" +
                                    "2. Мои сообщения\n" +
                                    "3. Мои уведомления\n" +
                                    "4. Изменить пароль\n" +
                                    "5. Изменить email\n" +
                                    "6. Выйти из учётной записи\n");

                            switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                case 1:
                                    userService.getAuthorisedUser(username);
                                    System.out.println();
                                    break;
                                case 2:
                                    System.out.println("Последние сообщения:\n");
                                    List<Message> messages = messageService.getMessage(username);
                                    if (messages.isEmpty()) {
                                        System.out.println("Нет сообщений для пользователя: " + username);
                                    } else {
                                        for (Message msg : messages) {
                                            System.out.println(msg);
                                        }
                                    }

                                    System.out.println("""
                                            1. Отправить сообщение
                                            2. Веруться назад
                                            """);

                                    userAction = Integer.parseInt(scanner.nextLine());
                                    if (userAction == 1) {
                                        System.out.println("Введите получателя:\n");
                                        userService.getUsersKeys();
                                        String receiverName = scanner.nextLine();

                                        User sender = userService.getUserByName(username);
                                        User receiver = userService.getUserByName(receiverName);

                                        System.out.println("Сообщение:");
                                        String content = scanner.nextLine();
                                        messageService.sendMessage(sender, receiver, content);
                                        break;
                                    } else if (userAction == 2) {
                                        break;
                                    }
                                case 3:
                                    do {
                                    System.out.println("Последние уведомления: " + user.getNotificationService()
                                            .notificationsCounter(username));
                                    System.out.println(user.getNotificationService().showNotifications(username));

                                        System.out.println("""
                                                1. Прочесть уведомление.
                                                2. Отметить все уведомления как прочтенные.
                                                3. Вернуться назад.""");
                                        switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                            case 1:
                                                if(user.getNotificationService().notificationsCounter(username) == 0) {
                                                    System.out.println("Нет уведомлений для прочтения.\n");
                                                } else {
                                                    user.getNotificationService().readNotification(username);
                                                }
                                                break;
                                            case 2:
                                                if (user.getNotificationService().notificationsCounter(username) == 0) {
                                                    System.out.println("Нет уведомлений для прочтения.\n");
                                                } else {
                                                    user.getNotificationService().readAllNotifications(username);
                                                }
                                                break;
                                            case 3:
                                                break;
                                        }
                                    } while (userAction != 3);
                                    break;
                                case 4:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();

                                    System.out.println("Введите старый пароль:");
                                    String oldPassword = scanner.nextLine();

                                    if (userService.checkUsername(username) && userService.checkPassword(username, oldPassword))
                                        System.out.println("""
                                                Введите новый пароль: \s
                                                ⦁ Пароль не должен быть пустым \s
                                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                    String newPassword = scanner.nextLine();
                                    if (userService.updatePassword(username, newPassword))
                                        logger.info("Пароль был успешно изменен. \n");
                                    break;
                                case 5:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();
                                    System.out.println();

                                    if (userService.checkUsername(username))
                                        System.out.println(("""
                                                Введите новый email:
                                                ⦁ Email не должен быть пустым \s
                                                ⦁ Должен содержать символ '@'"""));

                                    String newEmail = scanner.nextLine();
                                    if (userService.updateEmail(username, newEmail))
                                        logger.info("Email был успешно изменен. \n");
                                    break;
                                case 6:
                                    break;
                            }
                        } while (authenticationService.isAuthenticated(username, password) && userAction != 6);
                    }
                }
                case 6: {
                    break;
                }
            }
        } while (userAction != 3);
        scanner.close();
    }
}
