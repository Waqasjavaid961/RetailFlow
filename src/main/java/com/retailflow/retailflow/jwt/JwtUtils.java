package com.retailflow.retailflow.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${jwt.example.secretKey}")
    private String secretKey;

    @Value("${jwt.example.expirationTime}")
    private Long expirationTime;


    public SecretKey getSecretKey(){
        // StandardCharsets.UTF_8 is used to ensure cross-platform compatibility when reading bytes.
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generates a new JWT token for the authenticated user.
     */
    public String generateToken(String email){
        return Jwts.builder()
                .subject(email) // The 'subject' is typically the unique identifier of the user.
                .issuedAt(new Date()) // Current timestamp when token was created.
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // Expiration time.
                .signWith(getSecretKey()) // Digitally signing the token to prevent tampering.
                .compact(); // Builds the token string.
    }
    public String generateTokenWithStoreId(String email,Long  storeId){
        return Jwts.builder().
                subject(email)
                .claim("storeId",storeId).
                issuedAt(new Date()).
                expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(getSecretKey())
                .compact();
    }

    /**
     * Parses the token to extract all the claims (payload data).
     * Automatically throws exceptions if the token is tampered, malformed, or expired (if parser checks it).
     */
    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey()) // This is the crucial security check. It verifies the signature.
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Generic method to extract a specific piece of information from the token.
     */
    public <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts the user's email (subject) from the token.
     */
    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Long extractStoreId(String token){
        return extractClaims(token,Claims->
                Claims.get("storeId",Long.class));
    }


    /**
     * Checks whether the token has passed its expiration time.
     */
    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Validates if the token belongs to the user and is still active.
     */
    public boolean isTokenValid(String token, String userEmail){
        final String email = extractEmail(token);
        // FIX: The bug was checking `isTokenExpired(token)` instead of `!isTokenExpired(token)`.
        // A valid token MUST belong to the user AND NOT be expired.
        return (email.equals(userEmail) && !isTokenExpired(token));
    }
}
