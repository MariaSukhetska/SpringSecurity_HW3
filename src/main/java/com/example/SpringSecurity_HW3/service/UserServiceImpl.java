package com.example.SpringSecurity_HW3.service;

import com.example.SpringSecurity_HW3.exception.EntityNotFoundException;
import com.example.SpringSecurity_HW3.model.User;
import com.example.SpringSecurity_HW3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        log.info("Finding user by username {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(()
                        -> new UsernameNotFoundException("User not found with username: " + username));
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("Find user by id {}", id);
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        log.info("Retrieving all users");
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        log.info("Saving user {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User updatedUser) {
        log.info("Updating user with id: {}", id);
        return userRepository.findById(id)
                .map(user -> {
                    user.setUsername(updatedUser.getUsername());
                    user.setPassword(updatedUser.getPassword());
                    user.setUserRole(updatedUser.getUserRole());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
}
