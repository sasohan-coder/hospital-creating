package com.example.librarymanagement.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String adminHome(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin/index";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "admin/dashboard";
    }

    @GetMapping("/users")
    public String adminUsers(Model model) {
        // TODO: Add user management logic
        return "admin/users";
    }

    @GetMapping("/reports")
    public String adminReports(Model model) {
        // TODO: Add reports logic
        return "admin/reports";
    }

    @GetMapping("/invoices")
    public String adminInvoices(Model model) {
        // TODO: Add invoice management logic
        return "admin/invoices";
    }
}
