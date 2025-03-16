public class AccountAuthentication {

    private final User user;

    public AccountAuthentication(User user) {
        this.user = user;
    }

    public boolean isAuthenticated(String username, String password) {
        if (user == null) {
            System.out.println("Пользователь не найден.");
            return false;
        }
        return  (username != null && username.equals(user.getUsername()) &&
                user.checkPassword(password));
        }
}