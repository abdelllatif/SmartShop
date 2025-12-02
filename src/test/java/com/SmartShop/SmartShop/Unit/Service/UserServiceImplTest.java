package com.SmartShop.SmartShop.Unit.Service;

import com.SmartShop.SmartShop.dto.UserRequest;
import com.SmartShop.SmartShop.dto.UserResponse;
import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.mapper.UserMapper;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.repository.UserRepository;
import com.SmartShop.SmartShop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private UserRequest request;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // إعداد UserRequest
        request = new UserRequest();
        request.setUsername("justtest");
        request.setEmail("ana@gmail.com");
        request.setPassword("12345678");
        request.setRole(UserRole.CLIENT);

        // إعداد User حقيقي لتجنب NullPointer
        user = new User();
        user.setId(1L);
        user.setUsername("justtest");
        user.setEmail("ana@gmail.com");
        user.setPassword("12345678");
        user.setRole(UserRole.CLIENT);
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

    @Test
    void updateUser_ShouldUpdateAndSaveUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserRequest updateRequest = new UserRequest();
        updateRequest.setUsername("testjdid");
        updateRequest.setEmail("testjdid@gmail.com");
        updateRequest.setPassword("paasswordnew");
        updateRequest.setRole(UserRole.ADMIN);

        User updatedUser = userService.updateUser(1L, updateRequest);

        assertEquals("testjdid", updatedUser.getUsername());
        assertEquals("testjdid@gmail.com", updatedUser.getEmail());
        assertEquals(UserRole.ADMIN, updatedUser.getRole());
        verify(userRepository).save(user);
    }
}
