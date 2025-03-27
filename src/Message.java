import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Message {

    private String sender;
    private String receiver;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime timestamp;

    private final Map<String, List<Message>> messageStorage = new HashMap<>();
    private final File messagesFile = new File("messages.json");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = Logger.getLogger(Message.class.getName());

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public Message() {
        loadMessagesFromFile();
    }

    public Message(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }
    public void setContent() {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender + " -> " + receiver + ": " + content;
    }

    public Map<String, List<Message>> getMessageStorage() {
        return messageStorage;
    }

    public void sendMessage(String sender, String receiver, String content) {
        Message message = new Message(sender, receiver, content);
        messageStorage.computeIfAbsent(receiver, k -> new ArrayList<>()).add(message);
        System.out.println("Сообщение отправлено от " + sender + " пользователю " + receiver + ".\n");
        saveMessagesToFile();
    }

    public List<Message> getMessage(String receiver) {
        if (!messageStorage.containsKey(receiver)) {
            logger.warning("Сообщение для данного пользователя отсутствуют.");
            return Collections.emptyList();
        }
        return messageStorage.get(receiver);
    }

    private void saveMessagesToFile() {
        try {
            objectMapper.writeValue(messagesFile, messageStorage);
        } catch (IOException e) {
            logger.severe("Ошибка сохранения файла сообщений\n");
        }
    }

    private void loadMessagesFromFile() {
        try {
            Map<String, List<Message>> loadedMessages = objectMapper.readValue(messagesFile, new TypeReference<>() {});
            messageStorage.putAll(loadedMessages);
        } catch (IOException e) {
            logger.severe("Ошибка загрузки файла сообщений. Если отсутствует - будет создан новый файл\n");
        }
    }
}