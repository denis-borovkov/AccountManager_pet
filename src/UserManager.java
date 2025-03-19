import java.util.Map;
import java.util.HashMap;

public class UserManager {

    private final Validation validation = new Validation();
    private final Map<String, User> userData = new HashMap<>();

    public boolean createUser(String username, String password, String email) {
        if (!validation.isValidUsername(username)) {
            System.out.println("Неверное имя пользователя \n");
            return false;
        }
        if (validation.isValidPassword(password, username)) {
            System.out.println("Неверный пароль \n");
            return false;
        }
        if (validation.isValidEmail(email)) {
            System.out.println("Неверный email \n");
            return false;
        }
        User newUser = new User(username, password, email);
        userData.put(username, newUser);
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user = userData.get(username);
        if (user == null) {
            System.out.println("Пользователь не найден. \n");
            return false;
        }
        if (validation.isValidPassword(newPassword, username)) {
            System.out.println("Неверный пароль. \n");
            return false;
        }
        user.setPassword(newPassword);
        return true;
    }

    public boolean updateEmail(String username, String newEmail) {
        User user = userData.get(username);
        if (user == null) {
            System.out.println("Пользователь не найден \n");
            return false;
        }
        if (validation.isValidEmail(newEmail)) {
            System.out.println("Неверный формат email. \n");
            return false;
        }
        user.setEmail(newEmail);
        System.out.println("Email был успешно обновлен! \n");
        return true;
    }

    public boolean isAuthenticated(String username, String password) {
        User user = userData.get(username);
        if (user == null) {
            System.out.println("Нет пользователей для аутентификации \n");
            return false;
        }
        if (username == null || password == null) {
            System.out.println("Account and password cannot be null \n");
            return false;
        }
        if (username.equals(user.getUsername()) && user.checkPassword(password)) {
            return true;
        }
        System.out.println("Неудачная аутентификация \n");
        return false;
    }

    public String getAllUsers() {
        StringBuilder allUsers = new StringBuilder();
        for (Map.Entry<String, User> entry : userData.entrySet()) {
            allUsers.append("Логин: ")
                    .append(entry.getKey())
                    .append("\n")
                    .append("Email: ")
                    .append(entry.getValue().getEmail())
                    .append("\n");
        }
        return allUsers.toString();
    }

    public boolean checkUsername(String username) {
        return userData.containsKey(username);
    }

    public boolean checkPassword(String username, String password) {
        User user = userData.get(username);
        return user.checkPassword(password);
    }

    public boolean removeUser(String username) {
        if (userData.containsKey(username)) {
            userData.remove(username);
            return true;
        }
        System.out.println("Пользователь не найден \n");
        return false;
    }
}
