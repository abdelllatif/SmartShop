package com.SmartShop.SmartShop.service;

import com.SmartShop.SmartShop.dto.UserRequest;
import com.SmartShop.SmartShop.dto.UserResponse;
import com.SmartShop.SmartShop.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse register(UserRequest user);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User updateUser(Long id, UserRequest user);  // fixed to include id
    void deleteUser(Long id);
}
