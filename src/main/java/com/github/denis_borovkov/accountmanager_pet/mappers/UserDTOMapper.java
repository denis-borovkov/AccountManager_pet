package com.github.denis_borovkov.accountmanager_pet.mappers;

import com.github.denis_borovkov.accountmanager_pet.dto.UserDTO;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
