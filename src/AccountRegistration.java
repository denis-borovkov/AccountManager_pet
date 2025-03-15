import java.util.regex.Pattern;
import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    User user;
    private final Map <String, User> userData = new HashMap<>();

    public AccountRegistration(User user) {
        this.user = user;
    }

    public boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20)
            return false;
        return Pattern.matches("^[A-Za-z][A-Za-z0-9_]*$", username);
    }

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < 8 || password.equals(user.getUsername())) {
            return false;
        }
        return Pattern.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", password);
    }

    public boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", email);
    }

    public void addUsername(String username) {
        try {
            if (isValidUsername(username))
                user.setUsername(username);
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный логин \n");
        }
    }

    public void addPassword(String password) {
        try {
            if (isValidPassword(password))
                user.setPassword(password);
        } catch (IllegalStateException e) {
            System.out.println("Некорректный пароль \n");
        }
    }

    public void addEmail(String email) {
        try {
            if (isValidEmail(email))
                user.setEmail(email);
        } catch (IllegalStateException e) {
            System.out.println("Некорректный email \n");
        }
    }

    public boolean createUser() {
        if (isValidUsername(user.getUsername()) && isValidPassword(user.getPassword()) && isValidEmail(user.getEmail()))
            userData.put(user.getUsername(), user);
        return true;
    }

    public User getUser() {
        return userData.get(user.getUsername());
    }

    public void removeUser() {
        userData.remove(user.getUsername());
    }
}