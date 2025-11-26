package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.LoginRequest;
import com.SmartShop.SmartShop.exception.BadRequestException;
import com.SmartShop.SmartShop.exception.NotFoundException;
import com.SmartShop.SmartShop.exception.UnauthorizedException;
import com.SmartShop.SmartShop.model.User;
import com.SmartShop.SmartShop.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest, HttpSession session) {

        if (session.getAttribute("USER") != null) {
            throw new BadRequestException("You are already logged in.");
        }

        User user = userService.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new NotFoundException("Email not found."));

        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid password.");
        }

        session.setAttribute("USER", user);
        return "Login successful as " + user.getRole();
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("USER") == null) {
            throw new BadRequestException("You are not logged in.");
        }

        session.invalidate();
        return "Logout successful.";
    }

}
