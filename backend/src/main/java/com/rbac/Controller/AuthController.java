package com.rbac.Controller;

import com.rbac.DTO.LoginRequest;
import com.rbac.DTO.LoginResponse;
import com.rbac.DTO.RegisterRequest;
import com.rbac.Entity.User;
import com.rbac.Repository.UserRepository;
import com.rbac.Service.JwtTokenProvider;
import com.rbac.Service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    /**
     * Endpoint for registering a new user.
     *
     * @param request The registration request object containing username, password, and role.
     * @return ResponseEntity with a success message upon successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        // Register the user using the provided details from the request
        userService.registerUser(request.getUsername(), request.getPassword(), request.getRole());

        // Return a response indicating the user has been registered successfully
        return ResponseEntity.ok("User registered successfully!");
    }

    /**
     * Endpoint for logging in to the application.
     *
     * @param request The login request object containing the username and password.
     * @return ResponseEntity with a JWT token and the user object upon successful login.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Validate the user using the provided details from the request
            User user = userService.validateUser(request.getUsername(), request.getPassword());

            // Generate a JWT token for the user
            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole().getName());

            // Reset the user's password to avoid exposing the password
            user.setPassword(null);

            // Return a response containing the JWT token and the user object
            return ResponseEntity.ok(new LoginResponse(token, user));

        } catch (Exception ex) {
            // Return a 401 Unauthorized response if the login fails
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
    /**
     * Endpoint for logging out of the application.
     *
     * @param request  The HttpServletRequest object for retrieving the JWT token.

     * @return ResponseEntity with a success message upon logging out successfully.
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request  ){
        String token = getJwtFromRequest(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Add the token to a blacklist or invalidate it
            // This is a temporary solution until we implement a proper token revocation mechanism
            jwtTokenProvider.addToBlacklist(token);
        }
        return ResponseEntity.ok("Logged out successfully");
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
