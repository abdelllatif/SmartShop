package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.service.UserService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
            throw new RuntimeException("Unauthorized");
        }
        return userService.register(user);
    }

    @GetMapping("/me")
    public User getCurrentUser() {
        if(session.getAttribute("USER")==null) throw new RuntimeException("User not logged in");
        return (User) session.getAttribute("USER");
    }

    @GetMapping
    public List<User> getAllUsers() {
        PermissionChecker.canPerform(session,"READ_ALL");
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        PermissionChecker.canPerform(session,"READ");
        PermissionChecker.canAccessResource(session,id);
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        PermissionChecker.canPerform(session,"UPDATE");
        updatedUser.setId(id);
        return userService.updateUser(updatedUser);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        PermissionChecker.canPerform(session,"DELETE");
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
