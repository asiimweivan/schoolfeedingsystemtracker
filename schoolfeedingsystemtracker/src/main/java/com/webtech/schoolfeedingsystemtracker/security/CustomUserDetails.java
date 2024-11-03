package com.webtech.schoolfeedingsystemtracker.security;

import com.webtech.schoolfeedingsystemtracker.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private final User user; // User entity instance

    public CustomUserDetails(User user) {
        this.user = user; // Constructor to set the user
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Prefixing with "ROLE_" for Spring Security compatibility
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // Return user's password
    }

    @Override
    public String getUsername() {
        return user.getUsername(); // Return user's username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Account is not expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Account is not locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Credentials are not expired
    }

    @Override
    public boolean isEnabled() {
        return true; // Account is enabled
    }
}
