import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class NotificationService {

    private final Queue <String> notificationQueue = new LinkedList<>();
    private final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public void addNotification(String notification) {
        notificationQueue.offer(notification);
    }

    public String showNotifications() {
        return String.join("\n", notificationQueue);
    }

    public void readNotification() {
        if (notificationQueue.isEmpty()) {
            logger.warning("Очередь уведомлений пуста.\n");
        return;
        }
        notificationQueue.poll();
        logger.info("Запрос на чтение уведомления и удаление его из очереди.\n");
    }

    public int notificationsCounter() {
        return notificationQueue.size();
    }

    public void readAllNotifications() {
        notificationQueue.clear();
        System.out.println("Все уведомления были отмечены как прочитанные.\n");
    }
}
