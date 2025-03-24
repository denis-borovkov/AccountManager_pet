import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message {

    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;

    UserManager userManager = new UserManager();
    private final Map<String, List<Message>> messageStorage = new HashMap<>();

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + " -> " + receiver + ": " + content;
    }

    public void sendMessage(String sender, String receiver, String content) {
        if(userManager.userDatabase.containsKey(sender) || userManager.userDatabase.containsKey(receiver)) {
            System.out.println("Отправитель или получатель не найдены.");
            return;
        }
        Message message = new Message(sender, receiver, content);
        messageStorage.computeIfAbsent(receiver, k -> new ArrayList<>()).add(message);
        System.out.println("Сообщение отправлено от " + sender + " пользователю " + receiver + ".");
    }
}
