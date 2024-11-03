package com.webtech.schoolfeedingsystemtracker.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.webtech.schoolfeedingsystemtracker.services.PasswordResetService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
        try {
            String token = resetService.generateResetToken(email);
            resetService.sendResetEmail(token, email);
            model.addAttribute("message", "Password reset link sent to your email.");
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Email not found.");
        }
        return "forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        boolean isValidToken = resetService.validateToken(token);
        if (!isValidToken) {
            model.addAttribute("error", "Invalid or expired password reset token.");
            return "reset-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(
            @RequestParam("token") String token,
            @RequestParam("password") String password,
            Model model) {

        boolean isResetSuccessful = resetService.resetPassword(token, password);
        if (isResetSuccessful) {
            model.addAttribute("message", "Password reset successful. You can now log in.");
            return "login"; // Redirect to login page
        } else {
            model.addAttribute("error", "Failed to reset password. Invalid or expired token.");
            return "reset-password";
        }
    }
}
