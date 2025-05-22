package com.github.denis_borovkov.accountmanager_pet.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class AuthenticationRepository {
    private final Map<String, String> authData = new HashMap<>();

    public Map<String, String> getAuthData() {
        return authData;
    }



}
