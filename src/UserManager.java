import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

public class UserManager {

    private final Validation validation = new Validation();
    private final Permission permission = new Permission();
    private final Map<String, User> userDatabase = new HashMap<>();
    private final File storageFile = new File("users.json"); // Создает новый файл "users.json"
    private final ObjectMapper objectMapper = new ObjectMapper();  //


    public UserManager() {
        loadUsersFromFile();
    }

    public boolean createUser(String username, String password, String email) {
        if (userDatabase.containsKey(username)) {
            return false;
        }
        if (!validation.isValidUsername(username)) {
            System.out.println("Неверное имя пользователя \n");
            return false;
        }
        if (!validation.isValidPassword(password, username)) {
            System.out.println("Неверный пароль. \n");
            return false;
        }
        if (!validation.isValidEmail(email)) {
            System.out.println("Неверный email. \n");
            return false;
        }
            User newUser = new User(username, password, email, permission.setUserRole());
            userDatabase.put(username, newUser);
            saveUsersToFile();
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user = userDatabase.get(username);
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
        User user = userDatabase.get(username);
        if (user == null) {
            System.out.println("Пользователь не найден. \n");
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
        User user = userDatabase.get(username);
        if (user == null) {
            System.out.println("Нет пользователей для аутентификации. \n");
            return false;
        }
        if (username == null || password == null) {
            System.out.println("Account and password cannot be null! \n");
            return false;
        }
        if (username.equals(user.getUsername()) && user.checkPassword(password)) {
            return true;
        }
        System.out.println("Неудачная аутентификация \n");
        return false;
    }

    public boolean listUsers() {
        if (userDatabase.isEmpty()) {
            return false;
        }
        userDatabase.forEach(((username, user) ->
                System.out.println("Имя пользователя: " + username + "\nEmail: " + user.getEmail() + "\nРоль в системе: " + permission.getUserRole())));
        System.out.println();
        return true;
    }

    public boolean checkUsername(String username) {
        return userDatabase.containsKey(username);
    }

    public boolean checkPassword(String username, String password) {
        User user = userDatabase.get(username);
        return user.checkPassword(password);
    }

    public UserRole checkRole(UserRole userRole) {
        return userRole;
    }

    public boolean removeUser(String username) {
        if (userDatabase.containsKey(username)) {
            userDatabase.remove(username);
            saveUsersToFile();
            return true;
        }
        System.out.println("Пользователь не найден \n");
        return false;
    }

    private void saveUsersToFile() {
        try {
            objectMapper.writeValue(storageFile, userDatabase);
        } catch (IOException e) {
            System.err.println("Не удалось сохранить пользователей " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        if (!storageFile.exists()) {
            System.out.println("Не удалось найти файл пользователей \n");
            return;
        }
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userDatabase.putAll(loadedUsers);

        } catch (IOException e) {
            System.err.println("Не удалось загрузить пользователей \n");
        }
    }
}