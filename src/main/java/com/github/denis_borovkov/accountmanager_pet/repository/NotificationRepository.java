package com.github.denis_borovkov.accountmanager_pet.repository;

import com.github.denis_borovkov.accountmanager_pet.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Repository
public class NotificationRepository {
    private final Map<String, Queue<Notification>> notificationQueue = new HashMap<>();

    public Map<String, Queue<Notification>> getNotificationQueue() {
        return notificationQueue;
    }

}
