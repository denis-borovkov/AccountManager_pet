import java.util.Scanner;

public class AccountManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        UserManager userManager = new UserManager();

        int userAction = 0;

        do {
            System.out.println("""
                    Добро пожаловать! Выберите действие:\s
                    1. Зарегистрироваться\s
                    2. Войти\s
                    3. Просмотреть информацию о пользователе\s
                    4. Выйти\s
                    """);

            try {
                userAction = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException | IllegalStateException e) {
                System.out.println("Ошибка ввода. Введите число от 1 до 4 \n");
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
                            ⦁ Логин должен начинаться с буквы и может содержать буквы, цифры и символ подчеркивания \s""");

                    String username = "Kurwa228";

                        System.out.println();

                            System.out.println("""
                                Введите пароль: \s
                                ⦁ Пароль не должен быть пустым \s
                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                        String password = "12345678!Qq";
                        System.out.println();

                            System.out.println("""
                                Введите email:
                                ⦁ Email не должен быть пустым \s
                                ⦁ Должен содержать символ '@'""");

                        String email = "example@mail.ru";
                        System.out.println();

                        if (userManager.createUser(username, password, email))
                                System.out.println("Вы успешно зарегистрировались! \n");
                        else System.out.println("Ошибка регистрации \n");
                    break;
                }
                case 2: {

                        System.out.println("Войдите: ");

                        System.out.println("Введите логин: \n");
                        String username = "Kurwa228";

                        System.out.println("Введите пароль: \n");
                        String password = "12345678!Qq";

                        if (userManager.isAuthenticated(username,password))
                        System.out.println("Вы успешно вошли! \n");
                        else System.out.println("Неверный логин или пароль \n");
                }
                break;
                case 3: {
                    System.out.println("Просмотреть информацию о пользователе: \n");

                    if (!userManager.getAllUsers().isEmpty()) {
                        System.out.println(userManager.getAllUsers());

                        System.out.println("""
                                Выберите действие: \s
                                1. Удалить пользователя \s
                                2. Изменить пароль \s
                                3. Изменить email\s
                                5. Вернуться назад\s
                                """);
                        switch (userAction = Integer.parseInt(scanner.nextLine())) {

                            case 1:
                                System.out.println("Введите логин:");

                                String username = scanner.nextLine();
                                System.out.println();

                                if (userManager.checkUsername(username))
                                    if (userManager.removeUser(username))
                                        System.out.println("Пользователь успешно удален. \n");
                                break;
                            case 2:
                                System.out.println("Введите логин: \n");
                                String username1 = scanner.nextLine();

                                System.out.println("Введите старый пароль: \n");
                                String oldPassword = scanner.nextLine();

                                if (userManager.checkUsername(username1) && userManager.checkPassword(username1, oldPassword))
                                    System.out.println("""
                                            Введите новый пароль: \s
                                            ⦁ Пароль не должен быть пустым \s
                                            ⦁ Длина пароля должна быть не менее 8 символов \s
                                            ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                            одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                String newPassword = scanner.nextLine();
                                if (userManager.updatePassword(username1, newPassword))
                                    System.out.println("Пароль был успешно изменен. \n");
                                break;
                            case 3:
                                System.out.println("Введите логин: \n");
                                String username2 = scanner.nextLine();
                                System.out.println();

                                if (userManager.checkUsername(username2))
                                    System.out.println(("""
                                            Введите новый email:
                                            ⦁ Email не должен быть пустым \s
                                            ⦁ Должен содержать символ '@'"""));

                                String newEmail = scanner.nextLine();
                                if (userManager.updateEmail(username2, newEmail))
                                    System.out.println("Email был успешно изменен. \n");
                                break;
                            case 5:
                                break;
                        }
                    } else {
                        System.out.println("Нет доступной информации. \n");
                    }
                }
                    case 4: {
                        break;
                    }
            }
        } while (userAction != 4);
        scanner.close();
    }
}
