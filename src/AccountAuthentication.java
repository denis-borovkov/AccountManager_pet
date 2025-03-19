public class AccountAuthentication {

    User user;

    public boolean isAuthenticated(String username, String password) {
        if (user == null) {
            System.out.println("Нет пользователей для аутентификации \n");
            return false;
        }
        if (username == null || password == null) {
            System.out.println("Account and password cannot be null \n");
            return false;
        }
        if (username.equals(user.getUsername()) && user.checkPassword(password)) {
            System.out.println("Успешная аутентификация. \n");
            return true;
        }
        System.out.println("Неудачная аутентификация \n");
        return false;
    }
}