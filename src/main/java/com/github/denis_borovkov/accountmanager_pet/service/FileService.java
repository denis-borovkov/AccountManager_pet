package com.github.denis_borovkov.accountmanager_pet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.denis_borovkov.accountmanager_pet.config.FileConfig;
import com.github.denis_borovkov.accountmanager_pet.model.Message;
import com.github.denis_borovkov.accountmanager_pet.model.Notification;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.AuthenticationRepository;
import com.github.denis_borovkov.accountmanager_pet.repository.MessageRepository;
import com.github.denis_borovkov.accountmanager_pet.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

@Service
public class FileService {
    private final File storageFile = new File(FileConfig.STORAGE_FILE_PATH);
    private final File messagesFile = new File(FileConfig.MESSAGES_FILE_PATH);
    private final File notificationsFile = new File(FileConfig.NOTIFICATIONS_FILE_PATH);
    private final File authenticationDataFile = new File(FileConfig.AUTH_DATA_FILE_PATH);
    private final Logger logger = Logger.getLogger(this.getClass().getName());


    UserRepository userRepository;
    MessageRepository messageRepository;
    NotificationRepository notificationRepository;
    AuthenticationRepository authenticationRepository;

    ObjectMapper objectMapper;

    @Autowired
    public FileService(
            UserRepository userRepository,
            MessageRepository messageRepository,
            NotificationRepository  notificationRepository,
            AuthenticationRepository authenticationRepository ,
            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.notificationRepository = notificationRepository;
        this.authenticationRepository = authenticationRepository;
        this.objectMapper = objectMapper;
    }

    public void saveUsersToFile() {
        try {
            objectMapper.writeValue(storageFile, userRepository.getUserDatabase());
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.fillInStackTrace());
        }
    }

    public void loadUsersFromFile() {
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userRepository.addAll(loadedUsers);
        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей. Будет создан новый файл. \n" + e.getMessage());
        }
    }

    public void saveMessagesToFile() {
        try {
            objectMapper.writeValue(messagesFile, messageRepository.getMessageRepository());
        } catch (IOException e) {
            logger.severe("Ошибка сохранения файла сообщений\n");
        }
    }

    public void loadMessagesFromFile() {
        try {
            Map<String, List<Message>> loadedMessages = objectMapper.readValue(messagesFile, new TypeReference<>() {});
            messageRepository.getMessageRepository().putAll(loadedMessages);
        } catch (IOException e) {
            logger.severe("Ошибка загрузки файла сообщений. Будет создан новый файл\n");
        }
    }

    public void saveNotificationsToFile() {
        try {
            objectMapper.writeValue(notificationsFile, notificationRepository.getNotificationQueue());
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
            notificationRepository.getNotificationQueue().putAll(loadedNotifications);
        } catch (IOException e) {
            logger.severe("Ошибка загрузки уведомлений: " + e.getMessage());
        }
    }
    public void saveAuthDataToFile() {
        try {
            objectMapper.writeValue(authenticationDataFile, authenticationRepository.getAuthData());
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
            authenticationRepository.getAuthData().putAll(loadedAuthData);
        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей. Будет создан новый файл. \n" + e.getMessage());
        }
    }
}