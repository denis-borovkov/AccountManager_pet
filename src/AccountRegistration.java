import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

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

    public boolean isUsernameAvailable(String username) {
        return !userData.containsKey(username);
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
        System.out.println("Пароль был успешно обновлен! \n");
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
        if (userData.containsKey(username)) {
            System.out.println("Пользователь с таким логином уже существует.");
            return false;
        } else return true;
    }

    public boolean removeUser(String username) {
        if (userData.containsKey(username)) {
            userData.remove(username);
            System.out.println("Пользователь был успешно удалён! \n");
            return true;
        }
        System.out.println("Пользователь не найден \n");
        return false;
    }
}
