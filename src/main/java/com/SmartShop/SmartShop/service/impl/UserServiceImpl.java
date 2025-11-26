package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.dto.UserRequest;
import com.SmartShop.SmartShop.dto.UserResponse;
import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.mapper.UserMapper;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.repository.UserRepository;
import com.SmartShop.SmartShop.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse register(UserRequest request) {
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        if (user.getRole() == UserRole.CLIENT) {
            Client client = new Client();
            client.setUser(user);
            user.setClient(client);
        }
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
