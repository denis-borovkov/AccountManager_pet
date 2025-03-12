public class AccountAuthentication {

    User user;

    private String usernameAuth;
    private String passwordAuth;


    public AccountAuthentication(User user) {
        this.user = user;
    }

    public void setUsernameAuth(String usernameAuth) {
        this.usernameAuth = usernameAuth;
    }

    public void setPasswordAuth(String passwordAuth) {
        this.passwordAuth = passwordAuth;
    }

    public String getUsernameAuth() {
        return usernameAuth;
    }

    public String getPasswordAuth() {
        return passwordAuth;
    }

    public boolean inputCheck() {
        return getUsernameAuth().equals(user.getUsername()) &&
                getPasswordAuth().equals(user.getPassword());
    }
}