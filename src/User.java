import org.mindrot.jbcrypt.BCrypt;

public class User {

    private String username;
    private String password;
    private String email;
    private UserRole userRole;

    public User() {
    }

    public User(String username, String password, String email, UserRole userRole) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
        this.userRole = userRole;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}