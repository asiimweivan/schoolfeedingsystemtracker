package com.webtech.schoolfeedingsystemtracker.services;

import com.webtech.schoolfeedingsystemtracker.model.Audit;
import com.webtech.schoolfeedingsystemtracker.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {
    private final AuditRepository auditRepository;

    @Autowired
    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public List<Audit> getAllAuditLogs() {
        return auditRepository.findAll();
    }

    public void logAction(String action, String entityName, String user) {
        // Create a new Audit entry without needing to pass the timestamp
        Audit audit = new Audit(action, entityName, user); // Removed timestamp parameter
        auditRepository.save(audit);
    }

    public Audit getAuditById(Long id) {
        return auditRepository.findById(id).orElse(null);
    }
}
