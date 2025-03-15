import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    User user;
    private final Validation validation;
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

    public void updatePassword(User user, String newPassword) {
        if (user.getUsername() != null && !user.getUsername().isEmpty() &&
                checkUsername(user.getUsername()))
            if (validation.isValidPassword(newPassword))
                user.setPassword(newPassword);
        else System.out.println("Пароль не был обновлён \n");
    }

    public void addEmail(User user, String email) {
        if (user != null && validation.isValidEmail(email))
            user.setEmail(email);
        else System.out.println("Некорректный email \n");
    }

    public void updateEmail(User user, String newEmail) {
        if (user.getUsername() != null && !user.getUsername().isEmpty() &&
                checkUsername(user.getUsername()))
            if (validation.isValidEmail(newEmail))
                user.setEmail(newEmail);
            else System.out.println("Email не обновлён \n");
    }

    public boolean createUser() {
        if (validation.isValidUsername(user.getUsername()) &&
                validation.isValidPassword(user.getPassword()) &&
                validation.isValidEmail(user.getEmail()))
            userData.put(user.getUsername(), new User());
        return true;
    }

    public boolean checkUsername(String username) {
        return userData.containsKey(username);
    }

    public User getUser() {
            return user;
    }

    public void removeUser() {
            userData.remove(user.getUsername());
    }
}