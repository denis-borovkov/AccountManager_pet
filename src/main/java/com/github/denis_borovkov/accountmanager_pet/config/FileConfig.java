package com.github.denis_borovkov.accountmanager_pet.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    public static final String STORAGE_FILE_PATH = "src/main/resources/users.json";
    public static final String MESSAGES_FILE_PATH = "src/main/resources/messages.json";
    public static final String NOTIFICATIONS_FILE_PATH = "src/main/resources/notifications.json";
    public static final String AUTH_DATA_FILE_PATH = "src/main/resources/authdata.json";
}
