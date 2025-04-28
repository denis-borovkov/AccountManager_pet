package com.github.denis_borovkov.accountmanager_pet.interfaces;

import org.springframework.stereotype.Component;

@Component
public interface Command {
    void execute();
}
