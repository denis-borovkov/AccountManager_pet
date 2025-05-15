package com.github.denis_borovkov.accountmanager_pet.utility;

import com.github.denis_borovkov.accountmanager_pet.implementation.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JwtUtil {

    @Value("${app.secret.key}")
    private static String SECRET_KEY;
    @Value("${app.expiration.time}")
    private static int EXPIRATION_TIME;

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date())
                .expiration(new Date(EXPIRATION_TIME))
                .signWith(KEY)
                .compact();
    }

}
