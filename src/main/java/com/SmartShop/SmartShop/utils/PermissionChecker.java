package com.SmartShop.SmartShop.utils;

import com.SmartShop.SmartShop.enums.UserRole;
import com.SmartShop.SmartShop.model.User;
import jakarta.servlet.http.HttpSession;

public class PermissionChecker {

    public static boolean canPerform(HttpSession session, String action) {
        User user = (User) session.getAttribute("USER");
        if (user == null) return false;
        if (user.getRole() == UserRole.ADMIN) {
            return true;
        }
        if (user.getRole() == UserRole.CLIENT) {
            return action.equalsIgnoreCase("READ");
        }
        return false;
    }
    public static boolean canAccessResource(HttpSession session, Long ownerId) {
        User user = (User) session.getAttribute("USER");
        if (user == null) return false;
        if (user.getRole() == UserRole.ADMIN) {
            return true;
        }
        return user.getId().equals(ownerId);
    }
}
