package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Add this annotation
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService; // Injecting UserService to access user data

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by username
        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User not found with username: {}", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Log the successful retrieval
        logger.info("User found: {}", user.getUsername());
        // Return a CustomUserDetails object
        return new CustomUserDetails(user);
    }
}
