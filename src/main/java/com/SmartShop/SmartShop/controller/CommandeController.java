package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.dto.CommandeRequest;
import com.SmartShop.SmartShop.dto.CommandeResponse;
import com.SmartShop.SmartShop.exception.ForbiddenException;
import com.SmartShop.SmartShop.service.CommandeService;
import com.SmartShop.SmartShop.utils.PermissionChecker;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    public List<CommandeResponse> getAllCommandes() {
        if (!PermissionChecker.canPerform(session, "READ")) {
            throw new ForbiddenException("Access denied");
        }
        return commandeService.getAllCommandes();
    }

    @PutMapping("/{id}")
    public CommandeResponse updateCommande(@PathVariable Long id, @RequestBody CommandeRequest request) {
        if (!PermissionChecker.canPerform(session, "UPDATE")) {
            throw new ForbiddenException("Access denied");
        }
        return commandeService.updateCommande(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        if (!PermissionChecker.canPerform(session, "DELETE")) {
            throw new ForbiddenException("Access denied");
        }
        commandeService.deleteCommande(id);
    }
}
