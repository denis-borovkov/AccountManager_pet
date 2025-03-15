import java.util.Scanner;

public class AccountManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        User user = new User();
        AccountRegistration accountRegistration = new AccountRegistration(user);
        AccountAuthentication accountAuthentication = new AccountAuthentication(user);

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
                    accountRegistration.addUsername(user, "Kurwa228");
                        System.out.println();

                            System.out.println("""
                                Введите пароль: \s
                                ⦁ Пароль не должен быть пустым \s
                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");
                        accountRegistration.addPassword(user, "1234567!Ww");
                        System.out.println();

                            System.out.println("""
                                Введите email:
                                ⦁ Email не должен быть пустым \s
                                ⦁ Должен содержать символ '@'""");
                        accountRegistration.addEmail(user, "example@mail.com");
                        System.out.println();

                        accountRegistration.createUser();

                        if (accountRegistration.createUser())
                                System.out.println("Вы успешно зарегистрировались! \n");
                        else System.out.println("Ошибка регистрации \n");
                    break;
                }
                case 2: {

                    while (!accountAuthentication.isAuthenticated()) {
                        System.out.println("Войдите: ");

                        System.out.println("Введите логин: ");
                        accountAuthentication.setUsernameAuth(scanner.nextLine());

                        System.out.println("Введите пароль: ");
                        accountAuthentication.setPasswordAuth(scanner.nextLine());

                        System.out.println("Введите email: ");
                        accountAuthentication.setEmailAuth(scanner.nextLine());

                        if (accountAuthentication.isAuthenticated()) {
                            System.out.println("Вы успешно вошли! \n");
                        } else System.out.println("Неверный логин, либо пароль или email... \n");
                    }
                }
                break;
                case 3: {
                    System.out.println("Просмотреть информацию о пользователе: \n");

                    if (accountRegistration.getUser() != null) {
                        System.out.println("Имя пользователя: " + user.getUsername() + "\n"
                                + "Ваш email: " + user.getEmail() + "\n");
                    } else
                        System.out.println("Нет доступной информации \n");

                    System.out.println("""
                            Выберите действие: \s
                            1. Удалить пользователя \s
                            2. Изменить пароль \s
                            3. Изменить email\s
                            5. Вернуться назад\s
                            """);
                    switch (userAction = Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            if (accountRegistration.getUser() != null) {
                                System.out.println("Введите логин:");

                                if (accountRegistration.checkUsername(scanner.nextLine()))
                                    accountRegistration.removeUser();
                                System.out.println("Пользователь успешно удален. \n");
                            } else System.out.println("Произошла ошибка удаления пользователя.");
                            break;
                        case 2:
                            if (accountRegistration.getUser() != null) {
                                System.out.println("Введите логин:");

                                if (accountRegistration.checkUsername(scanner.nextLine()))
                                    System.out.println("""
                                            Введите новый пароль: \s
                                            ⦁ Пароль не должен быть пустым \s
                                            ⦁ Длина пароля должна быть не менее 8 символов \s
                                            ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                            одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");

                                accountRegistration.updatePassword(user, scanner.nextLine());
                                System.out.println("Пароль был успешно изменен. \n");
                                break;
                            } else {
                                System.out.println("Нет доступных зарегистрированных пользователей. \n");
                            }
                        case 3:
                            if (accountRegistration.getUser() != null) {
                                System.out.println("Введите логин:");

                                if (accountRegistration.checkUsername(scanner.nextLine()))
                                    System.out.println(("""
                                    Введите новый email:
                                    ⦁ Email не должен быть пустым \s
                                    ⦁ Должен содержать символ '@'"""));

                            accountRegistration.updateEmail(user, scanner.nextLine());
                            System.out.println("Email был успешно изменен. \n");
                            break;
                            } else {
                                System.out.println("Нет доступных зарегистрированных пользователей. \n");
                            }
                        case 5:
                            break;
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
