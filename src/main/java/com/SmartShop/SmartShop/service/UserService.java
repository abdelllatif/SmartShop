package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User register(User user);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(User user);
    void deleteUser(Long id);
}
