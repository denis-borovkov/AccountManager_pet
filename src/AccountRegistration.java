import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    User user;
    Validation validation;
    private final Map <String, User> userData = new HashMap<>();

    public AccountRegistration(User user) {
        this.user = user;
        this.validation = new Validation(user);
    }

    public void addUsername(User user, String username) {
        if (user != null && validation.isValidUsername(username))
            user.setUsername(username);
        else System.out.println("Некорректный логин \n");
    }


    public void addPassword(User user, String password) {
        if (user != null && validation.isValidPassword(password))
            user.setPassword(password);
        else System.out.println("Некорректный пароль \n");
    }

    public void addEmail(User user, String email) {
        if (user != null && validation.isValidEmail(email))
            user.setEmail(email);
        else System.out.println("Некорректный email \n");
    }

    public boolean createUser() {
        if (validation.isValidUsername(user.getUsername()) &&
                validation.isValidPassword(user.getPassword()) &&
                validation.isValidEmail(user.getEmail()))
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