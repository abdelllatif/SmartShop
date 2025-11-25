package com.SmartShop.SmartShop.service.impl;

import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.repositories.UserRepository;
import com.SmartShop.SmartShop.services.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) {
        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> login(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
