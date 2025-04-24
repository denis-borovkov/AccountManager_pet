package Service;

import Utility.JwtUtil;
import Model.User;
import Utility.SecurityUtil;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class AuthenticationService {
    private String username;
    private String userToken;
    private final Logger logger = Logger.getLogger(AuthenticationService.class.getName());
    private final Map<String, String> authData = new HashMap<>();
    FileService fileService = new FileService();
    UserRepository userRepository;

    public AuthenticationService() {
        fileService.loadAuthDataFromFile();
    }

    public Map<String, String> getAuthData() {
        return authData;
    }

    public void setUsername(String username) {
        User user  = userRepository.getUser(username);
        if (user != null) {
            this.username = username;
        } else {
            logger.warning("Пользователь не найден: " + username);
        }
    }

    public void setUserToken(String username) {
        if (username != null) {
            this.userToken = JwtUtil.generateToken(username);
        } else {
            logger.warning("Ошибка: невозможно создать токен для null пользователя.");
        }
    }

    public String getUserToken() {
        this.userToken = authData.get(username);
        if (userToken == null || userToken.isEmpty()) {
            logger.warning("Токен для " + username + " отсутствует.");
        }
        return userToken;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated(String username, String password) {
        User user  = userRepository.getUser(username);
        if (user == null) {
            logger.warning("Нет пользователей для аутентификации: " + username);
            return false;
        }
        if (username == null || password == null) {
            logger.warning("Ошибка введенных данных.");
            return false;
        }
        if (userRepository.exists(username) && SecurityUtil.checkPassword(password, user.getPassword())) {
            setUsername(username);
            setUserToken(username);
            authData.put(getUsername(), userToken);
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
        return JwtUtil.validateToken(userToken);
    }
}
