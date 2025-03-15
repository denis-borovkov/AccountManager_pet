import java.util.regex.Pattern;

public class Validation {

    User user;

    public Validation(User user) {
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
}
