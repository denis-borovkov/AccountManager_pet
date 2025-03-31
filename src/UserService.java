import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final Map<String, User> userDatabase = new HashMap<>();
    private final Validation validation = new Validation();
    FileService fileService = new FileService(this);

    public UserService() {
        fileService.loadUsersFromFile();
    }

    public Map<String, User> getUserDatabase() {
        return this.userDatabase;
    }

    public boolean createUser(String username, String password, String email) {
        if (userDatabase.containsKey(username)) {
            logger.warning("Пользователь с таким логином уже существует.");
            return false;
        }
        if (validation.isValidUsername(username)) {
            logger.warning("Неверный формат логина.");
            return false;
        }
        if (validation.isValidPassword(password, username)) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        if (validation.isValidEmail(email)) {
            logger.warning("Неверный формат email.");
            return false;
        }
            User newUser = new User(username, password, email, UserRole.USER);
            userDatabase.put(username, newUser);
            fileService.saveUsersToFile();
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
        fileService.saveUsersToFile();
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
        fileService.saveUsersToFile();
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

    public User getUserByName(String username) {
        for (User user : userDatabase.values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
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
        fileService.saveUsersToFile();
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
        fileService.saveUsersToFile();
    }

    public boolean isAdmin(String username) {
        User user = userDatabase.get(username);
        return user != null && user.getUserRole() == UserRole.ADMIN;
    }

    public boolean removeUser(String username) {
        if (userDatabase.containsKey(username)) {
            userDatabase.remove(username);
            fileService.saveUsersToFile();
            return true;
        }
        logger.warning("Пользователь не найден в файлах пользователей\n");
        return false;
    }

}