package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.model.Role;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.utility.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;

import java.util.logging.Logger;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class.getName());

    UserRepository userRepository;
    FileService fileService;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        fileService.loadUsersFromFile();
    }

    public Long generateId() {
        return System.nanoTime();
    }

    public boolean createUser(User user) {
        if (userRepository.exists(user.getUsername())) {
            logger.warning("Пользователь с таким логином уже существует.");
            return false;
        }
        if (ValidationUtil.isValidUsername(user.getUsername())) {
            logger.warning("Неверный формат логина.");
            return false;
        }
        if (ValidationUtil.isValidPassword(user.getPassword(), user.getUsername())) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        if (ValidationUtil.isValidEmail(user.getEmail())) {
            logger.warning("Неверный формат email.");
            return false;
        }
        userRepository.add(user);
        //fileService.saveUsersToFile();
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден.");
            return false;
        }
        if (ValidationUtil.isValidPassword(newPassword, username)) {
            logger.warning("Неверный формат пароля.");
            return false;
        }
        user.setPassword(newPassword);
        userRepository.replace(username, user);
        //fileService.saveUsersToFile();
        return true;
    }

    public boolean updateEmail(String username, String newEmail) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return false;
        }
        if (ValidationUtil.isValidEmail(newEmail)) {
            logger.warning("Неверный формат email." + newEmail);
            return false;
        }
        user.setEmail(newEmail);
        userRepository.replace(username, user);
        //fileService.saveUsersToFile();
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

    public void grantAdminRights(String username, String rootPassword) {
        User user = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            new Role(Role.RoleType.ADMIN);
        logger.info(username + " теперь имеет права: " + user.getRole());
        //fileService.saveUsersToFile();
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
        //fileService.saveUsersToFile();
    }

    public boolean isAdmin(User user) {
        user = userRepository.getUser(user.getUsername());
        return user != null && user.getRole().equals(Role.RoleType.ADMIN.toString());
    }

    public boolean removeUser(String username) {
        if (userRepository.exists(username)) {
            userRepository.remove(username);
            //fileService.saveUsersToFile();
            return true;
        }
        logger.warning("Пользователь не найден в файлах пользователей\n");
        return false;
    }
}