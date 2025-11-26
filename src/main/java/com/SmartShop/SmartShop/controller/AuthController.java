package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.LoginRequest;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
                                   HttpSession session) {

        if (session.getAttribute("USER") != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("You are already logged in.");
        }

        Optional<User> userOpt = userService.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        }
        User user = userOpt.get();

        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password.");
        }

        session.setAttribute("USER", user);
        return ResponseEntity.ok("Login successful as " + user.getRole());
    }

        @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        if (session.getAttribute("USER") == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("You are not logged in.");
        }

        session.invalidate();
        return ResponseEntity.ok("Logout successful.");
    }
}
