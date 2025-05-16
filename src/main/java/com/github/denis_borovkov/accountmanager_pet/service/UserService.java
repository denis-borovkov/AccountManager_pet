package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.implementation.UserDetailsImpl;
import com.github.denis_borovkov.accountmanager_pet.model.Role;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import com.github.denis_borovkov.accountmanager_pet.utility.ValidationUtil;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(String.format("User %s not found", username)
        ));
        return UserDetailsImpl.build(user);
    }


    public boolean createUser(User user) {
        if (userRepository.findUserByUsername(user.getUsername()).isPresent()) {
            logger.error("Пользователь с таким логином уже существует.");
            return false;
        }
        if (ValidationUtil.isValidUsername(user.getUsername())) {
            logger.error("Неверный формат логина.");
            return false;
        }
        if (ValidationUtil.isValidPassword(user.getPassword(), user.getUsername())) {
            logger.error("Неверный формат пароля.");
            return false;
        }
        if (ValidationUtil.isValidEmail(user.getEmail())) {
            logger.error("Неверный формат email.");
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean updatePassword(String username, String newPassword) {
        User user  = (User) loadUserByUsername(username);
        if (ValidationUtil.isValidPassword(newPassword, username)) {
            logger.warn("Неверный формат пароля.");
            return false;
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return true;
    }

    public boolean updateEmail(String username, String newEmail) {
        User user  = (User) loadUserByUsername(username);
        if (ValidationUtil.isValidEmail(newEmail)) {
            logger.warn("Неверный формат email.{}", newEmail);
            return false;
        }
        user.setEmail(newEmail);
        userRepository.save(user);
        return true;
    }

    public void listUsers() {
        if (userRepository.findAll().isEmpty()) {
            logger.warn("Нет сохраненных пользователей.");
            return;
        }
        userRepository.findAll().forEach(((user) ->
                System.out.println(
                        "Имя пользователя: " + user.getUsername() +
                        "\nEmail: " + user.getEmail() +
                        "\nРоль в системе: " + user.getRole())));
        System.out.println();
    }

    public void getAuthorisedUser(String username) {
        Optional<User> user  = userRepository.findUserByUsername(username);
        user.ifPresent(value -> System.out.println("Имя пользователя: " + username +
                "\nEmail: " + value.getEmail() +
                "\nРоль в системе: " + value.getRole()));
    }

    public User getUserByName(String username) {
        for (User user : userRepository.findAll()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public boolean checkUsername(String username) {
        User user  = (User) loadUserByUsername(username);
        if (user.getUsername().isEmpty()) {
            logger.error("Пользователь не найден.{}", username);
            return false;
        }
        return userRepository.findUserByUsername(username).isPresent();
    }

    public void grantAdminRights(String username, String rootPassword) {
        User user  = (User) loadUserByUsername(username);
        if (user.getUsername().isEmpty()) {
            logger.warn("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            new Role(Role.RoleType.ADMIN);
        logger.info("{} теперь имеет права: {}", username, user.getRole());
        //fileService.saveUsersToFile();
    }

    public void grantUserRights(String username, String rootPassword) {
        User user  = (User) loadUserByUsername(username);
        if (user == null) {
            logger.warn("Пользователь не найден." + username);
            return;
        }
        if (rootPassword.equals("777"))
            new Role(Role.RoleType.USER);
        logger.info(username + " теперь имеет права: " + user.getRole());
    }

    public boolean isAdmin(User user) {
        user = userRepository.getUserByUsername(user.getUsername());
        if (user != null) {
            return user.getRole().equals(Role.RoleType.ADMIN);
        }
        return false;
    }

    public boolean removeUser(String username) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            userRepository.removeUserByUsername(username);
            return true;
        }
        logger.warn("Пользователь не найден в файлах пользователей\n");
        return false;
    }
}