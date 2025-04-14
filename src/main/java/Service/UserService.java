package main.java.Service;

import main.java.Model.Role;
import main.java.Model.User;
import main.java.repository.UserRepository;

import java.util.logging.Logger;

public class UserService {

    private static final Logger logger = Logger.getLogger(UserService.class.getName());
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long generateId() {
        return System.nanoTime();
    }

    public boolean createUser(User user) {
        if (userRepository.exists(user.getUsername())) {
            logger.warning("Пользователь с таким логином уже существует.");
            return false;
        }
        if (ValidationService.isValidUsername(user.getUsername())) {
            logger.warning("Неверный формат логина.");
            return false;
        }
        if (ValidationService.isValidPassword(user.getPassword(), user.getUsername())) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        if (ValidationService.isValidEmail(user.getEmail())) {
            logger.warning("Неверный формат email.");
            return false;
        }
        userRepository.add(user);
        userRepository.saveToFile();
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден.");
            return false;
        }
        if (ValidationService.isValidPassword(newPassword, username)) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        user.setPassword(newPassword);
        userRepository.replace(username, user);
        userRepository.saveToFile();
        return true;
    }

    public boolean updateEmail(String username, String newEmail) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        if (ValidationService.isValidEmail(newEmail)) {
            logger.warning("Неверный формат email." + newEmail);
            return false;
        }
        user.setEmail(newEmail);
        userRepository.replace(username, user);
        userRepository.saveToFile();
        return true;
    }

    public void listUsers() {
        if (userRepository.isEmpty()) {
            logger.warning("Нет сохраненных пользователей.");
            return;
        }
        userRepository.getUserDatabase().forEach(((username, user) ->
                System.out.println(
                        "Имя пользователя: " + username +
                        "\nEmail: " + user.getEmail() +
                        "\nРоль в системе: " + user.getRole())));
        System.out.println();
    }

    public void getUsersKeys() {
        if (userRepository.isEmpty()) {
            logger.warning("Нет сохраненных пользователей.");
            return;
        }
        for (String key : userRepository.getUserDatabase().keySet()) {
            System.out.println(key);
        }
    }

    public void getAuthorisedUser(String username) {

        User user  = userRepository.getUser(username);
        System.out.println("Имя пользователя: " + username +
                "\nEmail: " + user.getEmail() +
                "\nРоль в системе: " + user.getRole());
    }

    public User getUserByName(String username) {
        for (User user : userRepository.getUserDatabase().values()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkUsername(String username) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        return userRepository.exists(username);
    }

    public boolean checkPassword(String username, String password) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        return user.checkPassword(password);
    }

    public void grantAdminRights(String username, String rootPassword) {
        User user = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            new Role(Role.RoleType.ADMIN);
        logger.info(username + " теперь имеет права: " + user.getRole());
        userRepository.saveToFile();
    }

    public void grantUserRights(String username, String rootPassword) {
        User user = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            new Role(Role.RoleType.USER);
        logger.info(username + " теперь имеет права: " + user.getRole());
        userRepository.saveToFile();
    }

    public boolean isAdmin(User user) {
        user = userRepository.getUser(user.getUsername());
        return user != null && user.getRole().equals(Role.RoleType.ADMIN.toString());
    }

    public boolean removeUser(String username) {
        if (userRepository.exists(username)) {
            userRepository.remove(username);
            userRepository.saveToFile();
            return true;
        }
        logger.warning("Пользователь не найден в файлах пользователей\n");
        return false;
    }
}