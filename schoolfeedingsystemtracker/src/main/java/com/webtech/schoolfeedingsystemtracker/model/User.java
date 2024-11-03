package com.webtech.schoolfeedingsystemtracker.model;

import com.webtech.schoolfeedingsystemtracker.audit.AuditListener;
import jakarta.persistence.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditListener.class) // Enable auditing for this entity
@Table(name = "users") // Specify table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullname; // Field for full name

    @Column(name = "username", unique = true, nullable = false)
    private String username; // Field for username

    @Column(name = "email", unique = true, nullable = false)
    private String email; // Field for email

    @Column(name = "password", nullable = false)
    private String password; // Field for password

    @Transient // Not stored in the database
    private String confirmPassword; // Field for password confirmation

    @Column(name = "role", nullable = false)
    private String role = "USER"; // Field for role with a default value of "USER"

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDateTime registrationDate; // Field for registration date

    // Default constructor
    public User() {
        this.registrationDate = LocalDateTime.now(); // Set default registration date to now
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
