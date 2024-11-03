package com.webtech.schoolfeedingsystemtracker.controllers;

import com.webtech.schoolfeedingsystemtracker.model.Audit;
import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.model.PageObject;
import com.webtech.schoolfeedingsystemtracker.services.UserService;
import com.webtech.schoolfeedingsystemtracker.services.PageService;
import com.webtech.schoolfeedingsystemtracker.services.AuditService; // Import the AuditService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PageService pageService;

    @Autowired
    private AuditService auditService; // Use AuditService instead of AuditInterface

    // Admin Dashboard with users list
    @GetMapping
    public String getAdminPage(@RequestParam(defaultValue = "0") int currentPage, Model model) {
        PageObject page = pageService.getPageContent(currentPage);
        model.addAttribute("page", page);
        return "admin";
    }

    // Show form for creating a new user
    @GetMapping("/users/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user";
    }

    // Handle form submission for creating a new user
    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/users";
    }

    // Show form for editing a user
    @GetMapping("/users/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "edit-user";
        }
        return "redirect:/admin/users";
    }

    // Handle form submission for updating a user
    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id, user);
        return "redirect:/admin/users";
    }

    // Delete a user by ID
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    // Admin Dashboard with users list, search, and sorting
    @GetMapping("/users")
    public String listUsers(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection,
            Model model) {

        Page<User> page = userService.searchUsersWithPagination(keyword, pageNo, pageSize, sortField, sortDirection);
        List<User> users = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("users", users);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("keyword", keyword);

        return "user-list";
    }

    // Get the audit logs
    @GetMapping("/audit-trail")
    public String getAuditLogs(Model model) {
        List<Audit> audits = auditService.getAllAuditLogs(); // Use the auditService method

        // No need to set formatted timestamps; they will be accessed through the getter
        model.addAttribute("audits", audits);
        return "audit-trail";
    }

    // Format the date for display
    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
