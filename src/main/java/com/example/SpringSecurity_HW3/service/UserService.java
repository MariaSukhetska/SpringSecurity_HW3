package com.example.SpringSecurity_HW3.service;

import com.example.SpringSecurity_HW3.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User findByUsername(String username);
    Optional<User> findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(Long id, User updatedUser);
    void delete (Long id);
}
