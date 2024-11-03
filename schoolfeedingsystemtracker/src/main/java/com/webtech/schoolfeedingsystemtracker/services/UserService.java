package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.Audit;
import com.webtech.schoolfeedingsystemtracker.model.User;
import com.webtech.schoolfeedingsystemtracker.repositories.AuditRepository;
import com.webtech.schoolfeedingsystemtracker.repositories.UserRepository;
import com.webtech.schoolfeedingsystemtracker.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditRepository auditRepository;

    public void save(User user) {
        userRepository.save(user);
        logAudit("User", "CREATE", user.getUsername());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void update(Long id, User user) {
        User existingUser = findById(id);
        if (existingUser != null) {
            existingUser.setFullname(user.getFullname());
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());
            userRepository.save(existingUser);
            logAudit("User", "UPDATE", existingUser.getUsername());
        }
    }

    public void delete(Long id) {
        User user = findById(id);
        if (user != null) {
            userRepository.deleteById(id);
            logAudit("User", "DELETE", user.getUsername());
        }
    }

    public boolean isValidUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        // Make sure to compare hashed passwords (not shown in this example)
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);
    }

    // Search, Pagination, Filtering, and Sorting

    public Page<User> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        return userRepository.findAll(pageable);
    }

    public Page<User> searchUsersWithPagination(String keyword, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        // Ensure the search query is case insensitive (this may depend on your implementation)
        return userRepository.searchUsersByKeywordWithPagination(keyword.toLowerCase(), pageable);
    }

    private void logAudit(String entityName, String action, String username) {
        // Removed the timestamp parameter as it's set in the Audit constructor
        Audit audit = new Audit(entityName, action, username);
        auditRepository.save(audit);
    }
}
