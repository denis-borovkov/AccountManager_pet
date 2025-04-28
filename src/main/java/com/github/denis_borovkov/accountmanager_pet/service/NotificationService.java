package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.model.Notification;
import com.github.denis_borovkov.accountmanager_pet.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

@Service
public class NotificationService {
    private final Logger logger = Logger.getLogger(NotificationService.class.getName());

    private final NotificationRepository notificationRepository;
    private final FileService fileService;

    public NotificationService(NotificationRepository notificationRepository, FileService fileService) {
        this.notificationRepository = notificationRepository;
        this.fileService = fileService;
        fileService.loadNotificationsFromFile();
    }

    public void addNotification(String username, String message) {
        notificationRepository.getNotificationQueue().computeIfAbsent(username, k -> new LinkedList<>()).offer(new Notification(username, message));
        fileService.saveNotificationsToFile();
    }

    public Queue<Notification> showNotifications(String username) {
        Queue<Notification> notifications = notificationRepository.getNotificationQueue().get(username);
        return notifications != null ? notifications : new LinkedList<>();
    }

    public void readNotification(String username) {
        if (!notificationRepository.getNotificationQueue().containsKey(username) || notificationRepository.getNotificationQueue().get(username).isEmpty()) {
            logger.warning("Очередь уведомлений пуста.\n");
            return;
        }
        notificationRepository.getNotificationQueue().get(username).poll();
        fileService.saveNotificationsToFile();
    }

    public int notificationsCounter(String username) {
        return notificationRepository.getNotificationQueue().getOrDefault(username, new LinkedList<>()).size();
    }

    public void readAllNotifications(String username) {
        notificationRepository.getNotificationQueue().remove(username);
        logger.info("Все уведомления были отмечены как прочитанные.\n");
        fileService.saveNotificationsToFile();
    }
}