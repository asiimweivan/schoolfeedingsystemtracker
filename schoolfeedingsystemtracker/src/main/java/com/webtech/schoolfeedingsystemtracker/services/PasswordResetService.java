package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.PasswordResetToken;
import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.repositories.PasswordResetTokenRepository;
import com.webtech.schoolfeedingsystemtracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    // Generate reset token and save it
    public String generateResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Generate token and save it with 1 hour validity
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, email, LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);

        return token;
    }

    // Send reset email
    public void sendResetEmail(String token, String email) {
        String resetLink = "http://localhost:8080/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetLink);
        mailSender.send(message);
    }

    // Validate token
    public boolean validateToken(String token) {
        Optional<PasswordResetToken> resetTokenOptional = tokenRepository.findByToken(token);
        return resetTokenOptional.isPresent() && resetTokenOptional.get().getExpirationTime().isAfter(LocalDateTime.now());
    }

    // Reset password
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> resetTokenOptional = tokenRepository.findByToken(token);
        if (resetTokenOptional.isPresent() && resetTokenOptional.get().getExpirationTime().isAfter(LocalDateTime.now())) {
            PasswordResetToken resetToken = resetTokenOptional.get();
            Optional<User> userOptional = userRepository.findByEmail(resetToken.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
                userRepository.save(user);
                tokenRepository.delete(resetToken);
                return true;
            }
        }
        return false;
    }
}
