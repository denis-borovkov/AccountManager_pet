package main.java.repository;

import main.java.Model.User;
import main.java.Service.FileService;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> userDatabase = new HashMap<>();

    public void add(User user) {
        userDatabase.put(user.getUsername(), user);
    }

    public void addAll(Map<String, User> users) {
        userDatabase.putAll(users);
    }

    public User getUser(String username) {
        return userDatabase.get(username);
    }

    public User listUsers() {
        return userDatabase.values();
    }

    public void remove(String username) {
        userDatabase.remove(username);
    }

    public void replace(String username, User user) {
        userDatabase.replace(username, user);
    }

    public void removeAll() {
        userDatabase.clear();
    }

    public boolean exists(String username) {
        return userDatabase.containsKey(username);
    }

    public boolean isEmpty() {
        return userDatabase.isEmpty();
    }

    public void saveToFile(FileService fileService) {
        fileService.saveUsersToFile((UserRepository) userDatabase);
    }

    public void loadFromFile(FileService fileService) {
        fileService.loadUsersFromFile((UserRepository) userDatabase);
    }
}
