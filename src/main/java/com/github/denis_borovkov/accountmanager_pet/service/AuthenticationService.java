package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.repository.AuthenticationRepository;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.utility.SecurityUtil;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthenticationService {
    private String username;
    private String userToken;
    private final Logger logger = Logger.getLogger(AuthenticationService.class.getName());


    private final FileService fileService;
    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(UserRepository userRepository, AuthenticationRepository authenticationRepository, FileService fileService) {
        this.userRepository = userRepository;
        this.authenticationRepository = authenticationRepository;
        this.fileService = fileService;
        fileService.loadAuthDataFromFile();
    }


    public void setUsername(String username) {
        Optional<User> user  = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            this.username = username;
        } else {
            logger.warning("Пользователь не найден: " + username);
        }
    }

    public void setUserToken(String username) {
        if (username != null) {
            //this.userToken = JwtUtil.generateToken(username);
        } else {
            logger.warning("Ошибка: невозможно создать токен для null пользователя.");
        }
    }

    public String getUserToken() {
        this.userToken = authenticationRepository.getAuthData().get(username);
        if (userToken == null || userToken.isEmpty()) {
            logger.warning("Токен для " + username + " отсутствует.");
        }
        return userToken;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated(String username, String password) {
        Optional<User> user  = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            logger.warning("Нет пользователей для аутентификации: " + username);
            return false;
        }
        if (username == null || password == null) {
            logger.warning("Ошибка введенных данных.");
            return false;
        }
        if (userRepository.existsByUsername(username) && SecurityUtil.checkPassword(password, user.get().getPassword())) {
            setUsername(username);
            setUserToken(username);
            authenticationRepository.getAuthData().put(getUsername(), userToken);
            if (!isTokenValid(getUserToken())) {
                logger.warning("Токен просрочен. Требуется повторная авторизация.");
                return false;
            }
            fileService.saveAuthDataToFile();
            return true;
        }
        logger.warning("Неудачная аутентификация.");
        return false;
    }

    public boolean isTokenValid(String userToken) {
        if (userToken == null || userToken.isEmpty()) {
            logger.warning("Токен отсутствует или пуст.");
            return false;
        }
        return true;
    }
}
