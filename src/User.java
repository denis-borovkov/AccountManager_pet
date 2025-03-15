import org.mindrot.jbcrypt.BCrypt;

public class User {

    private String username;
    private String password;
    private String email;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        if (username != null && !username.isEmpty()) return username;
        else return ("Логины отсутствует");
    }

    public String getPassword() {
        if (password != null && !password.isEmpty()) return password;
        else return ("Пароли отсутствует");
    }

    public String getEmail() {
        if (email != null && !email.isEmpty()) return email;
        else return ("Email отсутствует");
    }
}