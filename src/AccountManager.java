import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class AccountManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();
        Message message = new Message();
        Logger logger = Logger.getLogger(AccountManager.class.getName());

        int userAction = 0;

        do {
            System.out.println("""
                    Добро пожаловать! Выберите действие:\s
                    1. Зарегистрироваться\s
                    2. Войти в учетную запись\s
                    3. Выйти из программы\s
                    """);

            try {
                userAction = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException | IllegalStateException e) {
                logger.severe("Ошибка ввода. Введите корректную команду. \n");
                scanner.nextLine();
                continue;
            }
            switch (userAction) {
                case 1: {
                    System.out.println("Регистрация: \n");

                    System.out.println("""
                            Введите логин: \s
                            ⦁ Логин не должен быть пустым\s
                            ⦁ Длина логина должна быть не менее 3 символов и не более 20 \s
                            ⦁ Логин должен начинаться с буквы и может содержать буквы, цифры и символ подчеркивания \n""");

                    String username = scanner.nextLine();

                    System.out.println("""
                            Введите пароль: \s
                            ⦁ Пароль не должен быть пустым \s
                            ⦁ Длина пароля должна быть не менее 8 символов \s
                            ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                            одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *) \n""");

                    String password = scanner.nextLine();

                    System.out.println("""
                            Введите email:
                            ⦁ Email не должен быть пустым \s
                            ⦁ Должен содержать символ '@' \n""");

                    String email = scanner.nextLine();

                    if (userManager.createUser(username, password, email)) {
                        logger.info("Вы успешно зарегистрировались! \n");
                    } else {
                        logger.severe("Ошибка регистрации \n");
                    }
                    break;
                }
                case 2: {
                    System.out.println("Войдите: \n");

                    System.out.println("Введите логин: ");
                    String username = scanner.nextLine();
                    System.out.println("Введите пароль: ");
                    String password = scanner.nextLine();

                    if (userManager.isAuthenticated(username, password)) {
                        logger.info("Вы успешно вошли! \n");
                    } else {
                        logger.warning("Неверный логин или пароль \n");
                        break;
                    }
                    if (userManager.isAdmin(username)) {
                        do {
                            System.out.println("Добро пожаловать, " + username + "! Выберите действие:\n" +
                                    "1. Просмотреть информацию о пользователях\n" +
                                    "2. Удалить пользователя\n" +
                                    "3. Изменить пароль\n" +
                                    "4. Изменить email\n" +
                                    "5. Выйти из учётной записи\n");

                            switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                case 1:
                                    do {
                                        userManager.listUsers();
                                        System.out.println("""
                                                Выберите действие:\s
                                                1. Изменение пользовательских прав\s
                                                2. Вернуться назад\s
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
                                                    userManager.grantAdminRights(username, rootPassword);
                                                    userAction = 5;
                                                    break;
                                                } else if (userAction == 2) {
                                                    userManager.grantUserRights(username, rootPassword);
                                                    userAction = 5;
                                                    break;
                                                } else if (userAction == 3) {
                                                break;
                                            }
                                            case 2:
                                                break;
                                        }
                                    } while (userAction != 2);
                                    break;
                                case 2:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();
                                    System.out.println();

                                    System.out.println("Введите пароль:");
                                    password = scanner.nextLine();
                                    if (userManager.isAuthenticated(username, password))
                                        if (userManager.removeUser(username))
                                            logger.info("Пользователь успешно удален. \n");
                                    break;
                                case 3:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();

                                    System.out.println("Введите старый пароль:");
                                    String oldPassword = scanner.nextLine();

                                    if (userManager.isAuthenticated(username, oldPassword))
                                        System.out.println("""
                                                Введите новый пароль: \s
                                                ⦁ Пароль не должен быть пустым \s
                                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                    String newPassword = scanner.nextLine();
                                    if (userManager.updatePassword(username, newPassword))
                                        logger.info("Пароль был успешно изменен. \n");
                                    break;
                                case 4:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();
                                    System.out.println();

                                    if (userManager.checkUsername(username))
                                        System.out.println(("""
                                                Введите новый email:
                                                ⦁ Email не должен быть пустым \s
                                                ⦁ Должен содержать символ '@'"""));

                                    String newEmail = scanner.nextLine();
                                    if (userManager.updateEmail(username, newEmail))
                                        logger.info("Email был успешно изменен. \n");
                                    break;
                                case 5:
                                    break;
                            }
                        } while (userManager.isAuthenticated(username, password) && userAction != 5);
                    } else {
                        do {
                            System.out.println("Добро пожаловать, " + username + "! Выберите действие:\n" +
                                    "1. Просмотреть личную информацию\n" +
                                    "2. Мои сообщения\n" +
                                    "3. Изменить пароль\n" +
                                    "4. Изменить email\n" +
                                    "5. Выйти из учётной записи\n");

                            switch (userAction = Integer.parseInt(scanner.nextLine())) {
                                case 1:
                                    userManager.getAuthorisedUser(username);
                                    System.out.println();
                                    break;
                                case 2:
                                    System.out.println("Последние сообщения:\n");
                                    List<Message> messages = message.getMessage(username);
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
                                        System.out.println("Введите получателя:");
                                        userManager.listUsers();
                                        String receiver = scanner.nextLine();

                                        System.out.println("Сообщение:");
                                        String content = scanner.nextLine();
                                        message.sendMessage(username, receiver, content);
                                        break;
                                    } else if (userAction == 2) {
                                        break;
                                    }
                                case 3:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();

                                    System.out.println("Введите старый пароль:");
                                    String oldPassword = scanner.nextLine();

                                    if (userManager.checkUsername(username) && userManager.checkPassword(username, oldPassword))
                                        System.out.println("""
                                                Введите новый пароль: \s
                                                ⦁ Пароль не должен быть пустым \s
                                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                    String newPassword = scanner.nextLine();
                                    if (userManager.updatePassword(username, newPassword))
                                        logger.info("Пароль был успешно изменен. \n");
                                    break;
                                case 4:
                                    System.out.println("Введите логин:");
                                    username = scanner.nextLine();
                                    System.out.println();

                                    if (userManager.checkUsername(username))
                                        System.out.println(("""
                                                Введите новый email:
                                                ⦁ Email не должен быть пустым \s
                                                ⦁ Должен содержать символ '@'"""));

                                    String newEmail = scanner.nextLine();
                                    if (userManager.updateEmail(username, newEmail))
                                        logger.info("Email был успешно изменен. \n");
                                    break;
                                case 5:
                                    break;
                            }
                        } while (userManager.isAuthenticated(username, password) && userAction != 5);
                    }
                }
                case 5: {
                    break;
                }
            }
        } while (userAction != 3);
        scanner.close();
    }
}
