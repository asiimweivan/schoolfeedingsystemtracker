package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.PageObject;
import com.webtech.schoolfeedingsystemtracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PageService {

    @Autowired
    private UserService userService; // Inject the UserService

    private static final int PAGE_SIZE = 10; // Define your desired page size

    public PageObject getPageContent(int currentPage) {
        List<User> users = userService.findAll(); // Fetch all users
        long totalElements = users.size(); // Total number of users
        int totalPages = (int) Math.ceil((double) totalElements / PAGE_SIZE); // Calculate total pages

        // Validate currentPage
        if (currentPage < 0 || currentPage >= totalPages) {
            currentPage = 0; // Reset to the first page if invalid
        }

        // Calculate start and end indices for pagination
        int start = Math.min(currentPage * PAGE_SIZE, (int) totalElements);
        int end = Math.min(start + PAGE_SIZE, (int) totalElements);

        List<User> paginatedUsers = (start < end) ? users.subList(start, end) : Collections.emptyList(); // Get the users for the current page

        // Create and return a PageObject, including the PAGE_SIZE
        return new PageObject(paginatedUsers, totalPages, totalElements, currentPage, PAGE_SIZE);
    }
}
