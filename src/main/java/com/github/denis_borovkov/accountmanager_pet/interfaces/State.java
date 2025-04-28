package com.github.denis_borovkov.accountmanager_pet.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface State {
    enum userState {
        GUEST,
        ADMIN,
        USER
    }
    void showMenu();

}
