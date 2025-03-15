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

                    do {

                        System.out.println("""
                                Введите логин: \s
                                ⦁ Логин не должен быть пустым\s
                                ⦁ Длина логина должна быть не менее 3 символов и не более 20 \s
                                ⦁ Логин должен начинаться с буквы и может содержать буквы, цифры и символ подчеркивания \s""");
                        accountRegistration.addUsername(user, scanner.nextLine());
                        System.out.println();

                        System.out.println("""
                                Введите пароль: \s
                                ⦁ Пароль не должен быть пустым \s
                                ⦁ Длина пароля должна быть не менее 8 символов \s
                                ⦁ Пароль должен содержать хотя бы одну заглавную букву, \s
                                одну строчную, одну цифру и один специальный символ (например, !, @, #, $, %, ^, &, *)""");
                        accountRegistration.addPassword(user, scanner.nextLine());
                        System.out.println();

                        System.out.println("""
                                Введите email:
                                ⦁ Email не должен быть пустым \s
                                ⦁ Должен содержать символ '@'""");
                        accountRegistration.addEmail(user, scanner.nextLine());
                        System.out.println();

                        try {
                            if (accountRegistration.createUser())
                                System.out.println("Вы успешно зарегистрировались! \n");
                        } catch (IllegalStateException e) {
                            System.out.println("Ошибка регистрации \n");
                        }
                    } while (!accountRegistration.createUser());
                    break;
                }
                case 2: {
                    System.out.println("Войдите: \n");

                    System.out.println("Введите логин: ");
                    accountAuthentication.setUsernameAuth(scanner.nextLine());

                    System.out.println("Введите пароль: ");
                    accountAuthentication.setPasswordAuth(scanner.nextLine());

                    System.out.println("Введите email: ");
                    accountAuthentication.setEmailAuth(scanner.nextLine());

                    if (accountAuthentication.isAuthenticated()) {
                        System.out.println("Вы успешно вошли! \n");
                    } else System.out.println("Ошибка введения данных... \n");
                }
                break;
                case 3: {
                    System.out.println("Просмотреть информацию о пользователе: \n");
                    accountRegistration.getUser();

                    if (accountRegistration.getUser() != null)
                        System.out.println("Имя пользователя: " + user.getUsername() + "\n"
                                + "Пароль: " + user.getPassword() + "\n"
                                + "Ваш email: " + user.getEmail() + "\n");
                    else
                        System.out.println("Нет доступной информации \n");

                    System.out.println("""
                            Выберите действие: \s
                            1. Удалить информацию о пользователе \s
                            2. Изменить пароль \s
                            3. Вернуться назад\s
                            """);
                    switch (userAction = Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            accountRegistration.removeUser();
                            System.out.println("Пользователь успешно удален. \n");

                            break;
                        case 2:
                            try {
                                if (accountRegistration.getUser() != null) {
                                    System.out.println("Введите логин");
                                    user.checkUsername(scanner.nextLine());

                                    System.out.println("Введите новый пароль");
                                    user.updatePassword(scanner.nextLine());

                                    System.out.println("Пароль был успешно изменен. \n");

                                    break;
                                }
                            } catch (IllegalStateException e) {
                                System.out.println("Нет доступных зарегистрированных пользователей \n");
                            }
                        case 3:
                            System.out.println("Вернуться назад \n");
                    }
                }
                break;
                case 4: {
                    break;
                }
            }
        } while (userAction != 4);

        scanner.close();
    }
}
