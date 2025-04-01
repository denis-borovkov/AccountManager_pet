import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Logger;

public class NotificationService {
    private final Map<String, Queue<Notification>> notificationQueue = new HashMap<>();
    private final FileService fileService = new FileService(this);
    private final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public NotificationService() {
        fileService.loadNotificationsFromFile();
    }

    public Map<String, Queue<Notification>> getNotificationQueue() {
        return notificationQueue;
    }

    public void addNotification(String username, String message) {
        notificationQueue.computeIfAbsent(username, k -> new LinkedList<>()).offer(new Notification(username, message));
        fileService.saveNotificationsToFile();
    }

    public Queue<Notification> showNotifications(String username) {
        Queue<Notification> notifications = notificationQueue.get(username);
        return notifications != null ? notifications : new LinkedList<>();
    }

    public void readNotification(String username) {
        if (!notificationQueue.containsKey(username) || notificationQueue.get(username).isEmpty()) {
            logger.warning("Очередь уведомлений пуста.\n");
            return;
        }
        notificationQueue.get(username).poll();
        fileService.saveNotificationsToFile();
    }

    public int notificationsCounter(String username) {
        return notificationQueue.getOrDefault(username, new LinkedList<>()).size();
    }

    public void readAllNotifications(String username) {
        notificationQueue.remove(username);
        logger.info("Все уведомления были отмечены как прочитанные.\n");
        fileService.saveNotificationsToFile();
    }
}