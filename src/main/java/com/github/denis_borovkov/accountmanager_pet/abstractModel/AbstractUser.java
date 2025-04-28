package com.github.denis_borovkov.accountmanager_pet.abstractModel;

import com.github.denis_borovkov.accountmanager_pet.model.Role;
import com.github.denis_borovkov.accountmanager_pet.utility.SecurityUtil;
import org.springframework.stereotype.Component;


@Component
public abstract class AbstractUser {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role.RoleType role;

    public AbstractUser() {}

    public AbstractUser(Long id, String username, String password, String email, Role.RoleType role) {
        this.id = id;
        this.username = username;
        setPassword(password);
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
        this.password = SecurityUtil.hashPassword(password);
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
                + ", email: " + email
                + ", role: " + role;
    }
}
