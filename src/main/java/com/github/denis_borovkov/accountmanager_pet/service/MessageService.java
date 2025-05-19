package com.github.denis_borovkov.accountmanager_pet.service;

import com.github.denis_borovkov.accountmanager_pet.model.Message;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    FileService fileService;
    MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        //fileService.loadMessagesFromFile();
    }

    /*public void sendMessage(User sender, User receiver, String content) {
        Message message = new Message(sender.getUsername(), receiver.getUsername(), content);
        messageRepository.createMessage(receiver.getUsername(), message);
        System.out.println("Сообщение отправлено от "
                + sender.getUsername() + " пользователю "
                + receiver.getUsername() + ".\n");
        receiver.getNotificationService()
                .addNotification(receiver.getUsername(),"Новое сообщение от: "
                + sender.getUsername() + " для "
                + receiver.getUsername());
        fileService.saveMessagesToFile();
    }
*/
    public List<Message> showMessage(String receiver) {
        if (!messageRepository.exists(receiver)) {
            return Collections.emptyList();
        }
        return messageRepository.getMessage(receiver);
    }

    public void sendMessage(User sender, User receiver, String content) {
    }
}
