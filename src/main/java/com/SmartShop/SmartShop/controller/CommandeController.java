package com.SmartShop.SmartShop.controller;

import com.SmartShop.SmartShop.service.CommandeService;
import jakarta.servlet.http.HttpSession;

public class CommandeController {
    private final CommandeService commandeService;
    private final HttpSession session;

    public CommandeController(CommandeService commandeService, HttpSession session) {
        this.commandeService = commandeService;
        this.session = session;
    }
}
