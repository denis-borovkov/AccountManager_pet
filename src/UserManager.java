import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

public class UserManager {

    private static final Logger logger = Logger.getLogger(UserManager.class.getName());
    private final Validation validation = new Validation();
    public final Map<String, User> userDatabase = new HashMap<>();
    private final File storageFile = new File("users.json");
    private final ObjectMapper objectMapper = new ObjectMapper();

    public UserManager() {
        loadUsersFromFile();
    }

    public boolean createUser(String username, String password, String email) {
        if (userDatabase.containsKey(username)) {
            logger.warning("Пользователь с таким логином уже существует.");
            return false;
        }
        if (validation.isValidUsername(username)) {
            logger.warning("Неверный логин.");
            return false;
        }
        if (validation.isValidPassword(password, username)) {
            logger.warning("Неверный пароль.");
            return false;
        }
        if (validation.isValidEmail(email)) {
            logger.warning("Неверный email.");
            return false;
        }
            User newUser = new User(username, password, email, UserRole.USER);
            userDatabase.put(username, newUser);
            saveUsersToFile();
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден.");
            return false;
        }
        if (validation.isValidPassword(newPassword, username)) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        user.setPassword(newPassword);
        userDatabase.replace(username, user);
        saveUsersToFile();
        return true;
    }

    public boolean updateEmail(String username, String newEmail) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        if (validation.isValidEmail(newEmail)) {
            logger.warning("Неверный формат email." + newEmail);
            return false;
        }
        user.setEmail(newEmail);
        userDatabase.replace(username, user);
        saveUsersToFile();
        return true;
    }

    public boolean isAuthenticated(String username, String password) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Нет пользователей для аутентификации." + username);
            return false;
        }
        if (username == null || password == null) {
            logger.warning("Ошибка введенных данных.");
            return false;
        }
        if (userDatabase.containsKey(username) && user.checkPassword(password)) {
            return true;
        }
        logger.warning("Неудачная аутентификация.");
        return false;
    }

    public void listUsers() {
        if (userDatabase.isEmpty()) {
            logger.warning("Нет сохраненных пользователей.");
            return;
        }
        userDatabase.forEach(((username, user) ->
                System.out.println(
                        "Имя пользователя: " + username +
                        "\nEmail: " + user.getEmail() +
                        "\nРоль в системе: " + user.getUserRole())));
        System.out.println();
    }

    public void getAuthorisedUser(String username) {
        User user = userDatabase.get(username);
        System.out.println("Имя пользователя: " + username +
                "\nEmail: " + user.getEmail() +
                "\nРоль в системе: " + user.getUserRole());
    }

    public boolean checkUsername(String username) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        return userDatabase.containsKey(username);
    }

    public boolean checkPassword(String username, String password) {
        User user  = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        return user.checkPassword(password);
    }

    public void grantAdminRights(String username, String rootPassword) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            user.setUserRole(UserRole.ADMIN);
        logger.info(username + " теперь имеет права: " + user.getUserRole());
        saveUsersToFile();
    }

    public void grantUserRights(String username, String rootPassword) {
        User user = userDatabase.get(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            user.setUserRole(UserRole.USER);
        logger.info(username + " теперь имеет права: " + user.getUserRole());
        saveUsersToFile();
    }

    public boolean isAdmin(String username) {
        User user = userDatabase.get(username);
        return user != null && user.getUserRole() == UserRole.ADMIN;
    }

    public boolean removeUser(String username) {
        if (userDatabase.containsKey(username)) {
            userDatabase.remove(username);
            saveUsersToFile();
            return true;
        }
        logger.warning("Пользователь не найден в файлах пользователей\n");
        return false;
    }

    private void saveUsersToFile() {
        try {
            objectMapper.writeValue(storageFile, userDatabase);
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        if (!storageFile.exists()) {
            logger.warning("Не удалось найти файл пользователей. При первом сохранении будет создан новый файл. \n");
            return;
        }
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userDatabase.putAll(loadedUsers);

        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей \n" + e.getMessage());
        }
    }
}