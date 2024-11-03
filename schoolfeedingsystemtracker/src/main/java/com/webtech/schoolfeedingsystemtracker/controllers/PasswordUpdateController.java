package com.webtech.schoolfeedingsystemtracker.controllers;

import com.webtech.schoolfeedingsystemtracker.model.PasswordResetToken;
import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.repositories.PasswordResetTokenRepository;
import com.webtech.schoolfeedingsystemtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class PasswordUpdateController {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/update-password")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @RequestParam("password") String newPassword, Model model) {
        Optional<PasswordResetToken> resetTokenOptional = tokenRepository.findByToken(token);

        // Check for valid token presence and expiration
        if (resetTokenOptional.isEmpty() || resetTokenOptional.get().getExpirationTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token.");
            return "reset-password";
        }

        PasswordResetToken resetToken = resetTokenOptional.get();

        // Find the associated user by email
        Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "User not found.");
            return "reset-password";
        }

        User user = userOptional.get();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));

        // Save user and handle potential exceptions
        try {
            userRepository.save(user);
        } catch (Exception e) {
            model.addAttribute("error", "Failed to update password. Please try again.");
            return "reset-password"; // Return to reset password page on error
        }

        model.addAttribute("message", "Password successfully updated.");
        return "login"; // Redirect to login page after successful update
    }
}
