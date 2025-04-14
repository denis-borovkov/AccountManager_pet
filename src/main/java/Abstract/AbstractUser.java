package main.java.Abstract;

import main.java.Model.Role;
import main.java.interfaces.State;


public abstract class AbstractUser implements State {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role.RoleType role;

    public AbstractUser() {}

    public AbstractUser(Long id, String username, String password, String email, Role.RoleType role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public abstract void showMenu();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role.RoleType getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + ", username: " + username
                + ", password: " + password
                + ", email: " + email
                + ", role: " + role;
    }
}
