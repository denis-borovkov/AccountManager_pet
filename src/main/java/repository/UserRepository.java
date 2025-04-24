package repository;

import Model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private final Map<String, User> userDatabase = new HashMap<>();

    public UserRepository() {}

    public Map<String, User> getUserDatabase() {
        return userDatabase;
    }

    public void add(User user) {
        userDatabase.put(user.getUsername(), user);
    }

    public void addAll(Map<String, User> users) {
        userDatabase.putAll(users);
    }

    public User getUser(String username) {
        return userDatabase.get(username);
    }

    public Map<String, User> listUsers() {
        return userDatabase.entrySet().stream().collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue));
    }

    public void getUsers() {
            for(String key : userDatabase.keySet())
                System.out.println(key);
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
}
