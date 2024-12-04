package com.rbac.Service;

import com.rbac.Entity.Role;
import com.rbac.Entity.User;
import com.rbac.Repository.RoleRepository;
import com.rbac.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    

    /**
     * Registers a new user in the system.
     *
     * @param username The username of the new user.
     * @param password The password of the new user.
     * @param roleName The name of the role to be assigned to the user.
     * @return The saved User entity.
     * @throws RuntimeException if the username is already taken or if the role is not found.
     */
    public User registerUser(String username, String password, String roleName) {
        log.info("Registering a new user with username: {}", username);

        // Check if the username already exists in the repository
        if (userRepository.findByUsername(username).isPresent()) {
            log.error("Username is already taken!");
            throw new RuntimeException("Username is already taken!");
        }

        // Fetch the role from the repository based on roleName
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    log.error("Role not found!");
                    return new RuntimeException("Role not found!");
                });

        // Create a new User entity and set its properties
        User user = new User();
        user.setUsername(username);
        // Encode the password for security purposes before setting it
        log.info("Encoding password for user: {}", username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        // Save the new user entity to the repository and return it
        log.info("Saving new user: {}", username);
        return userRepository.save(user);
    }
    /**
     * Validates a user's login credentials.
     *
     * @param username The username provided by the user.
     * @param password The password provided by the user.
     * @return The user entity if the credentials are valid.
     * @throws RuntimeException if the username does not exist, or if the password is incorrect.
     */
    public User validateUser(String username, String password) {
        log.info("Validating user with username: {}", username);

        User user;

        // Check if the username exists in the repository
        log.info("Checking if the username exists in the repository");
        user = userRepository.findByUsername(username).orElseThrow(() -> {
            log.error("Username does not exist!");
            return new RuntimeException("Invalid username or password");
        });

        // Check if the password matches the one stored in the repository
        log.info("Checking if the password matches the one stored in the repository");
        if (!decodePassword(password, user.getPassword())) {
            log.error("Password does not match!");
            throw new RuntimeException("Invalid username or password!!");
        }

        // Return the valid user entity
        log.info("Returning the valid user entity");
        return user;
    }
    public boolean decodePassword(String password, String dbPassword) {
        return passwordEncoder.matches(password, dbPassword);
    }
}
