package com.rbac.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Integer jwtExpiration;
    public List<String> blacklistToken = new ArrayList<>();

    /**
     * Generates a JWT token for a given username and role.
     *
     * @param username the username of the user
     * @param role the role of the user
     * @return a signed JWT token
     */
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username) // Set the username as the subject of the token
                .claim("role", role) // Include role as a claim in the token
                .setIssuedAt(new Date()) // Set the current date as the issue date of the token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) // Set the expiration date based on the configured expiration time
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Sign the token with the HS512 algorithm and secret key
                .compact(); // Build the token and return it as a string
    }

    /**
     * Retrieves the username from the given JWT token.
     * @param token the JWT token from which to retrieve the username
     * @return the username of the user associated with the given token
     */
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                // Set the signing key to the secret key
                .setSigningKey(jwtSecret)
                // Parse the token
                .parseClaimsJws(token)
                // Get the body of the token
                .getBody()
                // Get the subject (username) from the body
                .getSubject();
    }

    /**
     * Validates a given JWT token.
     * @param token the JWT token to validate
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            // Parse the token using the secret key
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            // Return true if no exception was thrown
            return true;
        } catch (Exception ex) {
            // Return false if an exception was thrown
            return false;
        }
    }



    /**
     * Adds the given JWT token to the blacklist, preventing it from being
     * validated in the future.
     * @param token the JWT token to add to the blacklist
     */
    public void addToBlacklist(String token) {
        blacklistToken.add(token);
    }
    /**
     * Checks if the given JWT token is blacklisted.
     *
     * @param token the JWT token to check
     * @return true if the token is blacklisted, false otherwise
     */
    public Boolean isTokenBlacklisted(String token) {
        // Check if the blacklist is not empty and contains the token
        return !blacklistToken.isEmpty() && blacklistToken.contains(token);
    }
}