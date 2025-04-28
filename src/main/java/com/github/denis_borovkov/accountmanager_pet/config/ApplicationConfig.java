package com.github.denis_borovkov.accountmanager_pet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.denis_borovkov.accountmanager_pet.command.LoginUserCommand;
import com.github.denis_borovkov.accountmanager_pet.command.RegisterUserHandler;
import com.github.denis_borovkov.accountmanager_pet.context.UserContext;
import com.github.denis_borovkov.accountmanager_pet.repository.AuthenticationRepository;
import com.github.denis_borovkov.accountmanager_pet.repository.MessageRepository;
import com.github.denis_borovkov.accountmanager_pet.repository.NotificationRepository;
import com.github.denis_borovkov.accountmanager_pet.service.*;
import com.github.denis_borovkov.accountmanager_pet.utility.ConsoleUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;

@Configuration
public class ApplicationConfig {

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI();
    }

    @Bean
    public UserContext userContext() {
        return new UserContext();
    }

    @Bean
    public AuthenticationService authenticationService(UserRepository userRepository, AuthenticationRepository authenticationRepository, FileService fileService) {
        return new AuthenticationService(userRepository, authenticationRepository , fileService);
    }

    @Bean
    public MessageRepository messageRepository() {
        return new MessageRepository();
    }

    @Bean
    public LoginUserCommand loginUserCommand(AuthenticationService authenticationService) {
        return new LoginUserCommand(authenticationService);
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository();
    }

    @Bean
    public MessageService messageService(MessageRepository messageRepository) {
        return new MessageService(messageRepository);
    }

    @Bean
    public UserService userService(UserRepository userRepository, FileService fileService) {
        return new UserService(userRepository, fileService);
    }

    @Bean
    public RegisterUserHandler registerUserHandler() {
        return new RegisterUserHandler();
    }

    @Bean
    public MenuHandler menuHandler(UserService userService,AuthenticationService authenticationService, UserRepository userRepository, RegisterUserHandler registerUserHandler) {
        return new MenuHandler(userService, authenticationService, registerUserHandler, userRepository);
    }

    @Bean
    public FileService fileService(UserRepository userRepository, MessageRepository messageRepository, ObjectMapper objectMapper, NotificationRepository notificationRepository, AuthenticationRepository authenticationRepository) {
        return new FileService(userRepository, messageRepository,notificationRepository, authenticationRepository, objectMapper);
    }

    @Bean
    public NotificationService notificationService(NotificationRepository notificationRepository, FileService fileService) {
        return new NotificationService(notificationRepository, fileService);
    }
}
