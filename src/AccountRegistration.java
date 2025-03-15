import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    User user;
    private final Map <String, User> userData = new HashMap<>();

    public AccountRegistration(User user) {
        this.user = user;
    }

    public void addUsername(String username) {
        user.setUsername(username);
    }

    public void addPassword(String password) {
        user.setPassword(password);
    }

    public void addEmail(String email) {
        user.setEmail(email);
    }

    public void createUser() {
        userData.put(user.getUsername(), user);
    }

    public User getUser() {
        return userData.get(user.getUsername());
    }

    public void removeUser() {
        userData.remove(user.getUsername());
    }
}