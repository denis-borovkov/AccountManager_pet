import java.util.NoSuchElementException;
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

                    System.out.println("Вы успешно зарегистрировались! \n");
                }
                break;
                case 2: {
                    System.out.println("Войдите: \n");

                    System.out.println("Введите логин: ");
                    authentication.setUsernameAuth(scanner.nextLine());

                    System.out.println("Введите пароль: ");
                    authentication.setPasswordAuth(scanner.nextLine());

                    if (authentication.inputCheck()) {
                        System.out.println("Вы успешно вошли");
                    } else System.out.println("Неверный логин или пароль");
                }
                break;
                case 3: {
                    System.out.println("Просмотреть информацию о пользователе: \n");

                    try {
                            System.out.println("Ваш логин: \n" + user.getUsername());
                            System.out.println("Ваш пароль: \n" + user.getPassword());
                    } catch (NoSuchElementException e) {
                        System.out.println("Нет доступной информации \n");
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
