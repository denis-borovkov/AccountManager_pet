public class AccountRegistration {

    User user;

    public AccountRegistration(User user) {
        this.user = user;
    }

    public void addUsername(String username) {
        user.setUsername(username);
    }

    public void addPassword(String password) {
        user.setPassword(password);
    }
}