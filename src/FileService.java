import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FileService {

    private final File storageFile = new File("users.json");
    private final File messagesFile = new File("messages.json");
    private final MessageService messageService = new MessageService();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(FileService.class.getName());
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void saveUsersToFile() {
        try {
            objectMapper.writeValue(storageFile, userService.getUserDatabase());
        } catch (IOException e) {
            logger.severe("Не удалось сохранить пользователей " + e.getMessage());
        }
    }

    public void loadUsersFromFile() {
        if (!storageFile.exists() || storageFile.length() == 0) {
            logger.warning("Не удалось найти файл пользователей. При первом сохранении будет создан новый файл. \n");
            return;
        }
        try {
            Map<String, User> loadedUsers = objectMapper.readValue(storageFile, new TypeReference<>() {});
            userService.getUserDatabase().putAll(loadedUsers);

        } catch (IOException e) {
            logger.severe("Не удалось загрузить пользователей \n" + e.getMessage());
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
            logger.severe("Ошибка загрузки файла сообщений. Если отсутствует - будет создан новый файл\n");
        }
    }
}
