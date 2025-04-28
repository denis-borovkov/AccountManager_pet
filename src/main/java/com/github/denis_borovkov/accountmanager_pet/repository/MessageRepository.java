package com.github.denis_borovkov.accountmanager_pet.repository;

import com.github.denis_borovkov.accountmanager_pet.model.Message;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MessageRepository {

    public MessageRepository() {}

    private final Map<String, List<Message>> messageStorage = new HashMap<>();

    public Map<String, List<Message>> getMessageRepository() {
        return messageStorage;
    }

    public void add(User user, List<Message> message) {
        messageStorage.put(user.getUsername(), message);
    }

    public void addAll(Map<String, List<Message>> messages) {
        messageStorage.putAll(messages);
    }

    public List<Message> getMessage(String username) {
        return messageStorage.get(username);
    }

    public Map<String, List<Message>> listMessages() {
        return messageStorage.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public void getUsers() {
        for(String key : messageStorage.keySet())
            System.out.println(key);
    }

    public void remove(String username) {
        messageStorage.remove(username);
    }

    public void replace(String username, List<Message> message) {
        messageStorage.replace(username, message);
    }

    public void removeAll() {
        messageStorage.clear();
    }

    public boolean exists(String username) {
        return messageStorage.containsKey(username);
    }

    public boolean isEmpty() {
        return messageStorage.isEmpty();
    }

    public void createMessage(String username, Message message) {
        messageStorage.computeIfAbsent(username, k -> new ArrayList<>()).add(message);
    }
}
