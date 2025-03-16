import java.util.Map;
import java.util.HashMap;

public class AccountRegistration {

    User user;
    private final Validation validation;
    private final Map <String, String> userData = new HashMap<>();

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
            if (validation.isValidPassword(newPassword)) {
                user.setPassword(newPassword);
                userData.replace(user.getUsername(), user.getPassword());
            }
            else System.out.println("Пароль не был обновлён \n");
    }

    public void addEmail(User user, String email) {
        if (validation.isValidEmail(email))
            user.setEmail(email);
        else System.out.println("Некорректный email \n");
    }

    public void updateEmail(User user, String newEmail) {
            if (validation.isValidEmail(newEmail))
                user.setEmail(newEmail);
            else System.out.println("Email не обновлён \n");
    }

    public boolean createUser() {
        if (validation.isValidUsername(user.getUsername()) &&
                validation.isValidPassword(user.getPassword()) &&
                validation.isValidEmail(user.getEmail()))
            userData.put(user.getUsername(), user.getPassword());
        return true;
    }

    public String getAllUsers() {
        for (Map.Entry<String, String> entry : userData.entrySet()) {
            return entry.getKey() + " " + entry.getValue();
            }
        return null;
    }

    public boolean checkUsername(String username) {
        return userData.containsKey(username);
    }

    public void removeUser() {
            userData.remove(user.getUsername());
            user.setUsername(null);
            user.setPassword(null);
    }
}