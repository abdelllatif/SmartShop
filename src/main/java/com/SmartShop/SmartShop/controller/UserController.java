package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.exception.ForbiddenException;
import com.SmartShop.SmartShop.exception.NotFoundException;
import com.SmartShop.SmartShop.exception.UnauthorizedException;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.service.UserService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final HttpSession session;

    public UserController(UserService userService, HttpSession session) {
        this.userService = userService;
        this.session = session;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        if(!PermissionChecker.canPerform(session,"CREATE")){
            throw new ForbiddenException("You are not allowed to create users");
        }
        return userService.register(user);
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        if(session.getAttribute("USER")==null) throw new UnauthorizedException("User not logged in");
        return (User) session.getAttribute("USER");
    }

    @GetMapping
    public List<User> getAllUsers() {
        if(!PermissionChecker.canPerform(session,"READ_ALL")){
            throw new ForbiddenException("Access denied");
        }
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        if(!PermissionChecker.canPerform(session,"READ")){
            throw new ForbiddenException("Access denied");
        }
        PermissionChecker.canAccessResource(session,id);
        return userService.getUserById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        if(!PermissionChecker.canPerform(session,"UPDATE")){
            throw new ForbiddenException("Access denied");
        }
        updatedUser.setId(id);
        return userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        if(!PermissionChecker.canPerform(session,"DELETE")){
            throw new ForbiddenException("Access denied");
        }
        userService.deleteUser(id);
        return "User deleted successfully";
    }

}
