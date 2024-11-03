package com.webtech.schoolfeedingsystemtracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Controller
public class LanguageController {

    private final LocaleResolver localeResolver;
    private final List<String> supportedLanguages = Arrays.asList("en", "fr", "rw"); // Add supported languages

    public LanguageController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @GetMapping("/changeLanguage")
    public String changeLanguage(@RequestParam("lang") String lang,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        // Validate the language parameter against supported languages
        if (!supportedLanguages.contains(lang)) {
            lang = "en"; // Default to English if no valid language is provided
        }

        Locale locale = new Locale(lang); // Set the locale based on the lang parameter
        localeResolver.setLocale(request, response, locale); // Set the locale for the session

        return "redirect:/"; // Redirect to the homepage or the intended page
    }
}
