package com.SmartShop.SmartShop.utils;

import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.model.User;
import jakarta.servlet.http.HttpSession;

public class PermissionChecker {
    public static boolean hasRole(HttpSession session, String role){
        User user =(User) session.getAttribute("user");
        if (user == null) return false;
        return user.getRole().equals(role);
    }
    public static boolean canPerform(HttpSession session, String role, String action) {
        User user = (User) session.getAttribute("USER");
        if (user == null) return false;

        if(user.getRole().equals(UserRole.ADMIN.toString())) {
            return true;
        }

        if(user.getRole().equals(UserRole.CLIENT.toString())) {
            return action.equalsIgnoreCase("READ");
        }

        return false;
    }
}
