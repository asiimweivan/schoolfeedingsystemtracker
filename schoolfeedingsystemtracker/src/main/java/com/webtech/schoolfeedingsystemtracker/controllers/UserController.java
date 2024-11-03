package com.webtech.schoolfeedingsystemtracker.controllers;

import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Registration GET method
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Returns 'register.html'
    }

    // Registration POST method
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userService.findByUsername(user.getUsername()) == null) {
            if (user.getRole() == null || user.getRole().isEmpty()) {
                user.setRole("USER"); // Assign default role if not specified
            }
            userService.save(user); // Save the new user
            redirectAttributes.addFlashAttribute("message", "Registration Successful");
            return "redirect:/login"; // Redirect to login page
        } else {
            redirectAttributes.addFlashAttribute("error", "Username already exists");
            return "redirect:/register"; // Redirect back to registration page
        }
    }

    // Login GET method
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login"; // Returns 'login.html'
    }

    // Login POST method
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        if (userService.isValidUser(user.getUsername(), user.getPassword())) {
            return "redirect:/home"; // Redirect to the home page after successful login
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid username or password");
            return "redirect:/login"; // Redirect back to login page on failure
        }
    }

    // Home GET method
    @GetMapping("/home")
    public String showHomePage(Model model) {
        // Pass welcome message to the home page
        model.addAttribute("welcomeMessage", "Welcome to the School Feeding Tracker");
        return "home"; // This will map to 'home.html'
    }
}
