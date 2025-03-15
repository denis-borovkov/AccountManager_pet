import org.mindrot.jbcrypt.BCrypt;

public class AccountAuthentication {

    User user;

    private String usernameAuth;
    private String passwordAuth;
    private String emailAuth;


    public AccountAuthentication(User user) {
        this.user = user;
    }

    public void setUsernameAuth(String usernameAuth) {
        this.usernameAuth = usernameAuth;
    }

    public void setPasswordAuth(String passwordAuth) {
        this.passwordAuth = passwordAuth;
    }

    public void setEmailAuth(String emailAuth) {
        this.emailAuth = emailAuth;
    }

    public String getUsernameAuth() {
        return usernameAuth;
    }

    public String getPasswordAuth() {
        return passwordAuth;
    }

    public String getEmailAuth(){
        return emailAuth;
    }

    public boolean isAuthenticated() {
        return  (getUsernameAuth().equals(user.getUsername()) &&
                BCrypt.checkpw(getPasswordAuth(), user.getPassword()) &&
                getEmailAuth().equals(user.getEmail()));
        }
}