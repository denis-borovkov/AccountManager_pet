import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageService {

    private final Map<String, List<Message>> messageStorage = new HashMap<>();
    FileService fileService = new FileService(this);

    public MessageService() {
        fileService.loadMessagesFromFile();
    }

    public Map<String, List<Message>> getMessageStorage() {
        return messageStorage;
    }

    public void sendMessage(User sender, User receiver, String content) {
        Message message = new Message(sender.getUsername(), receiver.getUsername(), content);
        messageStorage.computeIfAbsent(receiver.getUsername(), k -> new ArrayList<>()).add(message);
        System.out.println("Сообщение отправлено от " + sender.getUsername() + " пользователю " + receiver.getUsername() + ".\n");
        receiver.getNotificationService().addNotification("Новое сообщение от: " + sender.getUsername() + " для " + receiver.getUsername());
        fileService.saveMessagesToFile();
    }

    public List<Message> getMessage(String receiver) {
        if (!messageStorage.containsKey(receiver)) {
            return Collections.emptyList();
        }
        return messageStorage.get(receiver);
    }
}
