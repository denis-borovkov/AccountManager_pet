package com.github.denis_borovkov.accountmanager_pet.model;

import org.springframework.stereotype.Component;

@Component
public class Role {

    public enum RoleType {
        ADMIN, USER, GUEST
    }

    private RoleType role;


    public Role(RoleType role) {
        this.role = role;
    }

    public Role() {}

    public RoleType getRole(User user) {
        return role;
    }

    public RoleType setAdminRole(User user) {
        return RoleType.ADMIN;
    }

    public RoleType setUserRole(User user) {
        return RoleType.USER;
    }

    @Override
    public String toString() {
        return role.name();
    }
}
