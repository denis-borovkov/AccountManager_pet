import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    private final Validation validation = new Validation();
    private final Map <String, User> userData = new HashMap<>();

    public void addUsername(User user, String username) {
        if (user != null && validation.isValidUsername(username)) {
            user.setUsername(username);
            userData.put(username, user);
        } else System.out.println("Некорректный логин \n");
    }

    public void addPassword(User user, String password) {
        if (user != null && validation.isValidPassword(password, user.getUsername())) {
            user.setPassword(password);
            userData.put(user.getUsername(), user);
        } else System.out.println("Некорректный пароль \n");
    }

    public void updatePassword(User user, String newPassword) {
        if (user != null && validation.isValidPassword(newPassword, user.getUsername())) {
            user.setPassword(newPassword);
            userData.replace(user.getUsername(), user);
        }
        else System.out.println("Пароль не был обновлён \n");
    }

    public void addEmail(User user, String email) {
        if (user != null &&validation.isValidEmail(email)) {
            user.setEmail(email);
            userData.put(user.getUsername(), user);
        } else System.out.println("Некорректный email \n");
    }

    public void updateEmail(User user, String newEmail) {
        if (user != null && validation.isValidEmail(newEmail)) {
            user.setEmail(newEmail);
            userData.replace(user.getUsername(), user);
        } else System.out.println("Email не обновлён \n");
    }

    public boolean createUser(String username, String password, String email) {
        if (userData.containsKey(username)) {
            System.out.println("Пользователь с таким логином уже существует.");
            return false;
        }
        User user = new User(username, password, email);
        userData.put(user.getUsername(), user);
        System.out.println("Пользователь успешно добавлен");
        return true;
    }

    public String getAllUsers() {
        StringBuilder allUsers = new StringBuilder();
        for (Map.Entry<String, User> entry : userData.entrySet()) {
            allUsers.append("Логин: ").append(entry.getKey())
                    .append("Email: ")
                    .append(entry.getValue().getEmail())
                    .append("\n");
            }
        return allUsers.toString();
    }

    public boolean checkUsername(String username) {
        return userData.containsKey(username);
    }

    public void removeUser(String username) {
        if (userData.containsKey(username)) {
            userData.remove(username);
            System.out.println("Пользователь удалён \n");
        } else {
            System.out.println("Пользователь не найден \n");
        }
    }
}