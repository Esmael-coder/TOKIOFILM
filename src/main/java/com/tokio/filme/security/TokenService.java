package com.tokio.filme.security;

import com.tokio.filme.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Component
public class TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(CustomUserDetails userDetails){

        User user = userDetails.getUser();

        String role = user.getRoles().stream()
                .map(role1 -> role1.getAuthority().toString())
                .findAny()
                .orElseThrow();

        return Jwts.builder()
                .issuer("auth-api")
                .subject(user.getUsername())
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignKey())
                .compact();
    }

    public boolean isTheSameUser(String token, UserDetails user){

        String tokenUser = extractUserName(token);
        return (tokenUser.equals(user.getUsername()));
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token inválido", e);
            return false;
        }
    }


    public String extractUserName(String token) {
        return extractClaim(token, claims -> claims.getSubject());
    }


    /**
     * Extrai qualquer claim do token
     */

    private <T> T extractClaim(String token, Function<Claims, T> resolver){

        Claims claims = extractAllClaims(token);

        return resolver.apply(claims);
    }

    /**
     * Extrai todas as claims do token
     */
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
