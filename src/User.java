import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mindrot.jbcrypt.BCrypt;

public class User implements Serializable {

    private String username;
    private String password;
    private String email;
    private UserRole userRole;

    @JsonIgnore
    private NotificationService notificationService;

    @JsonIgnore
    private UserService userService;

    public User() {
    }

    public User(UserService userService, NotificationService notificationService) {
        this.userService = userService;
        this.notificationService = notificationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public User(String username, String password, String email, UserRole userRole) {
        this.username = username;
        setPassword(password);
        this.email = email;
        this.userRole = userRole;
        this.notificationService = new NotificationService();
    }

    public NotificationService getNotificationService() {
        if (notificationService == null)
            this.notificationService = new NotificationService();
        return notificationService;
    }

    @JsonIgnore
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @JsonIgnore
    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public UserRole getUserRole() {
        return this.userRole;
    }
}
