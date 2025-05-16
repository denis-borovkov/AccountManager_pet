package com.github.denis_borovkov.accountmanager_pet.repository;

import com.github.denis_borovkov.accountmanager_pet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    void removeUserByUsername(String username);

    User getUserByUsername(String username);

    boolean existsByUsername(String username);
}
