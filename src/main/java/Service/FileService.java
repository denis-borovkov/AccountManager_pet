package main.java.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.java.Model.Message;
import main.java.Model.Notification;
import main.java.Model.User;
import main.java.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class FileService {

    private final File storageFile = new File("src/main/resources/users.json");
    private final File messagesFile = new File("src/main/resources/messages.json");
    private final File notificationsFile = new File("src/main/resources/notifications.json");
    private final File authenticationDataFile = new File("src/main/resources/authdata.json");

    private MessageService messageService;
    private UserService userService;
    private NotificationService notificationService;
    private AuthenticationService authenticationService;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(FileService.class.getName());

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public FileService(UserService userService) {
        this.userService = userService;
    }

    public FileService(MessageService messageService) {
        this.messageService = messageService;
    }

    public FileService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public FileService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void saveUsersToFile(UserRepository userDatabase) {
        try {
            objectMapper.writeValue(storageFile, userDatabase.getUserDatabase());
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.fillInStackTrace());
        }
    }

    public void loadUsersFromFile(UserRepository userDatabase) {
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userDatabase.addAll(loadedUsers);
        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей. Будет создан новый файл. \n" + e.getMessage());
        }
    }

    public void saveMessagesToFile() {
        try {
            objectMapper.writeValue(messagesFile, messageService.getMessageStorage());
        } catch (IOException e) {
            logger.severe("Ошибка сохранения файла сообщений\n");
        }
    }

    public void loadMessagesFromFile() {
        try {
            Map<String, List<Message>> loadedMessages = objectMapper.readValue(messagesFile, new TypeReference<>() {});
            messageService.getMessageStorage().putAll(loadedMessages);
        } catch (IOException e) {
            logger.severe("Ошибка загрузки файла сообщений. Будет создан новый файл\n");
        }
    }

    public void saveNotificationsToFile() {
        try {
            objectMapper.writeValue(notificationsFile, notificationService.getNotificationQueue());
        } catch (IOException e) {
            logger.severe("Ошибка сохранения уведомлений: " + e.getMessage());
        }
    }

    public void loadNotificationsFromFile() {
        if (!notificationsFile.exists()) {
            logger.warning("Файл уведомлений не найден, создаётся новый.");
            return;
        }
        try {
            Map<String, Queue<Notification>> loadedNotifications = objectMapper.readValue(notificationsFile, new TypeReference<>() {});
            notificationService.getNotificationQueue().putAll(loadedNotifications);
        } catch (IOException e) {
            logger.severe("Ошибка загрузки уведомлений: " + e.getMessage());
        }
    }
    public void saveAuthDataToFile() {
        try {
            objectMapper.writeValue(authenticationDataFile, authenticationService.getAuthData());
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.getMessage());
        }
    }

    public void loadAuthDataFromFile() {
        if (!authenticationDataFile.exists()) {
            logger.warning("Файл пользовательских ключей не найден, создаётся новый.");
            return;
        }
        try {
            Map<String, String> loadedAuthData = objectMapper.readValue(authenticationDataFile, new TypeReference<>() {});
            authenticationService.getAuthData().putAll(loadedAuthData);
        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей. Будет создан новый файл. \n" + e.getMessage());
        }
    }
}