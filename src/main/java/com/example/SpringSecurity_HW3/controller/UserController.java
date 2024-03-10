package com.example.SpringSecurity_HW3.controller;

import com.example.SpringSecurity_HW3.exception.EntityNotFoundException;
import com.example.SpringSecurity_HW3.model.User;
import com.example.SpringSecurity_HW3.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("GET request for user with username: {}", username);
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("GET request for all users");
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MANAGER')")
    @GetMapping("/user/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("GET request for user with id: {}", id);
        User user = userService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        log.info("POST request to create user: {}", user.getUsername());
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        log.info("PUT request to update user with id: {}", id);
        User user = userService.update(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("DELETE request for user with id: {}", id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
