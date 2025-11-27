package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.CommandeRequest;
import com.SmartShop.SmartShop.dto.CommandeResponse;
import com.SmartShop.SmartShop.exception.ForbiddenException;
import com.SmartShop.SmartShop.service.CommandeService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class CommandeController {
    private final CommandeService commandeService;
    private final HttpSession session;

    public CommandeController(CommandeService commandeService, HttpSession session) {
        this.commandeService = commandeService;
        this.session = session;
    }

    @PostMapping
    public CommandeResponse createCommande(@RequestBody CommandeRequest request) {
        if (!PermissionChecker.canPerform(session, "CREATE")) {
            throw new ForbiddenException("Access denied");
        }
        return commandeService.createCommande(request);
    }
    @GetMapping("/{id}")
    public CommandeResponse getCommande(@PathVariable Long id) {
        if (!PermissionChecker.canPerform(session, "READ")) {
            throw new ForbiddenException("Access denied");
        }
        return commandeService.getCommande(id);
    }

}
