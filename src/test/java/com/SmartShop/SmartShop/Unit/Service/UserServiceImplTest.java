package com.SmartShop.SmartShop.Unit.Service;


import com.SmartShop.SmartShop.dto.UserRequest;
import com.SmartShop.SmartShop.dto.UserResponse;
import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.mapper.UserMapper;
import com.SmartShop.SmartShop.model.Client;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.repository.UserRepository;
import com.SmartShop.SmartShop.service.UserService;
import com.SmartShop.SmartShop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest request;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new UserRequest();
        request.setUsername("justtest");
        request.setEmail("ana@gmail.com");
        request.setPassword("12345678");
        request.setRole(UserRole.CLIENT);
    }


    @Test
    void register_ShouldSaveUserAndReturnResponse() {
        when(userMapper.toUser(request)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toUserResponse(user)).thenReturn(new UserResponse());

        UserResponse response = userService.register(request);

        assertNotNull(response);
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    void getAllUsers_ShouldReturnUserList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("justtest", result.get(0).getUsername());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals("justtest", result.get().getUsername());
    }

    @Test
    void getUserById_ShouldReturnEmptyOptional_WhenNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isEmpty());
    }


}
