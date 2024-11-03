package com.webtech.schoolfeedingsystemtracker.controllers;

import com.webtech.schoolfeedingsystemtracker.dto.AuditDTO;
import com.webtech.schoolfeedingsystemtracker.model.Audit;
import com.webtech.schoolfeedingsystemtracker.services.AuditService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/audit")
public class AuditController {
    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/audit-trail")
    public String getAuditLogs(Model model) {
        List<Audit> audits = auditService.getAllAuditLogs();
        model.addAttribute("audits", audits);
        return "audit-trail"; // Matches the Thymeleaf template 'audit-trail.html'
    }

    @PostMapping("/api/audit-trail") // Changed to /api/audit-log
    public ResponseEntity<String> logAudit(@Valid @RequestBody AuditDTO auditDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder("Validation errors: ");
            bindingResult.getFieldErrors().forEach(error ->
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ")
            );
            return ResponseEntity.badRequest().body(errors.toString());
        }

        auditService.logAction(auditDTO.getAction(), auditDTO.getEntityName(), auditDTO.getUser());
        return ResponseEntity.ok("Audit logged successfully");
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) return ""; // Handle null value
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }
}
