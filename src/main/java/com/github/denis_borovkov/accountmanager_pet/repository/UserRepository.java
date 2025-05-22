package com.github.denis_borovkov.accountmanager_pet.repository;

import com.github.denis_borovkov.accountmanager_pet.dto.UserDTO;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    void removeUserByUsername(String username);

    UserDTO getUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
