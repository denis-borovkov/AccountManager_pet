package com.github.denis_borovkov.accountmanager_pet.controllers;

import com.github.denis_borovkov.accountmanager_pet.dto.SigninRequestDTO;
import com.github.denis_borovkov.accountmanager_pet.dto.SignupRequestDTO;
import com.github.denis_borovkov.accountmanager_pet.model.User;
import com.github.denis_borovkov.accountmanager_pet.repository.UserRepository;
import com.github.denis_borovkov.accountmanager_pet.utility.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public SecurityController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager,
                              JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody SignupRequestDTO signupRequestDTO) {
        if (userRepository.existsByUsername(signupRequestDTO.username())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        if (userRepository.existsByEmail(signupRequestDTO.email())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDTO.password());
        User user = new User();
        user.setUsername(signupRequestDTO.username());
        user.setPassword(encodedPassword);
        user.setEmail(signupRequestDTO.email());
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequestDTO signinRequestDTO) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    signinRequestDTO.username(), signinRequestDTO.password()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
