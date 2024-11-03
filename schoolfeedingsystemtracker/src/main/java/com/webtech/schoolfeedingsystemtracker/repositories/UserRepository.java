package com.webtech.schoolfeedingsystemtracker.repositories;

import com.webtech.schoolfeedingsystemtracker.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    User findByUsername(String username);

    // Find user by email
    Optional<User> findByEmail(String email);

    // Custom search query with pagination and sorting
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.fullname) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<User> searchUsersByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);

    // Find users by registration date range
    List<User> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    // Count users by registration date
    long countByRegistrationDate(LocalDate registrationDate);

    // Find users by fullname containing a specific string (case insensitive)
    List<User> findByFullnameContainingIgnoreCase(String fullname);
}
