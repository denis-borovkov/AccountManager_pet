package main.java.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import main.java.Utility.Message;
import main.java.Utility.Notification;
import main.java.Utility.User;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class FileService {

    private final File storageFile = new File("main/resources/users.json");
    private final File messagesFile = new File("main/resources/messages.json");
    private final File notificationsFile = new File("main/resources/notifications.json");
    private final File authenticationDataFile = new File("main/resources/authdata.json");

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

    public void saveUsersToFile() {
        try {
            objectMapper.writeValue(storageFile, userService.getUserDatabase());
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.getMessage());
        }
    }

    public void loadUsersFromFile() {
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userService.getUserDatabase().putAll(loadedUsers);
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