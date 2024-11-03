package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.ExternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ExternalUser> getExternalUsers() {
        String url = "https://jsonplaceholder.typicode.com/users"; // Example API
        try {
            ExternalUser[] users = restTemplate.getForObject(url, ExternalUser[].class);
            return List.of(users); // Convert array to list
        } catch (HttpClientErrorException e) {
            System.err.println("Error fetching data: " + e.getStatusCode());
            return List.of(); // Return empty list on error
        }
    }
}
