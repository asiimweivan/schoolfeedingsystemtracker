package com.webtech.schoolfeedingsystemtracker.controllers;

import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.model.ExternalUser; // Ensure you import ExternalUser
import com.webtech.schoolfeedingsystemtracker.services.UserService;
import com.webtech.schoolfeedingsystemtracker.services.ApiService; // Import the ApiService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Base mapping for the controller
public class UserApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApiService apiService; // Inject the ApiService

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        // Check if the username already exists
        if (userService.findByUsername(user.getUsername()) == null) {
            userService.save(user); // Save the new user
            return new ResponseEntity<>(user, HttpStatus.CREATED); // Return created user
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT); // Conflict if username exists
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        if (userService.isValidUser(user.getUsername(), user.getPassword())) {
            return new ResponseEntity<>("Login Successful", HttpStatus.OK); // Successful login
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED); // Unauthorized
        }
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Not found
        }
    }

    // Update user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.update(id, user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get external users (previously in ApiController)
    @GetMapping("/external") // Changed endpoint to avoid conflict
    public ResponseEntity<List<ExternalUser>> getApiUsers() {
        List<ExternalUser> externalUsers = apiService.getExternalUsers();
        return new ResponseEntity<>(externalUsers, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
