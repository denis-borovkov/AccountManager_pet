import java.util.Scanner;

public class AccountManager {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        User user = new User();
        AccountRegistration accountRegistration = new AccountRegistration(user);
        AccountAuthentication authentication = new AccountAuthentication(user);

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

                    System.out.println("Введите логин: ");
                    accountRegistration.addUsername(scanner.nextLine());

                    System.out.println("Введите пароль: ");
                    accountRegistration.addPassword(scanner.nextLine());

                    System.out.println("Введите email: ");
                    accountRegistration.addEmail(scanner.nextLine());

                    accountRegistration.createUser();

                    System.out.println("Вы успешно зарегистрировались! \n");
                }
                break;
                case 2: {
                    System.out.println("Войдите: \n");

                    System.out.println("Введите логин: ");
                    authentication.setUsernameAuth(scanner.nextLine());

                    System.out.println("Введите пароль: ");
                    authentication.setPasswordAuth(scanner.nextLine());

                    System.out.println("Введите email: ");
                    authentication.setEmailAuth(scanner.nextLine());

                    if (authentication.isAuthenticated()) {
                        System.out.println("Вы успешно вошли");
                    } else System.out.println("Ошибка введения данных...");
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
                            if (accountRegistration.getUser() != null) {
                                System.out.println("Введите логин");
                                user.checkUsername(scanner.nextLine());

                                System.out.println("Введите новый пароль");
                                user.updatePassword(scanner.nextLine());

                                System.out.println("Пароль был успешно изменен. \n");
                                break;
                            } else System.out.println("Нет доступных зарегистрированных пользователей \n");
                            break;
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
