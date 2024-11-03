package com.webtech.schoolfeedingsystemtracker.audit;

import com.webtech.schoolfeedingsystemtracker.model.Audit;
import com.webtech.schoolfeedingsystemtracker.repositories.AuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditInterface {

    private final AuditRepository auditRepository;

    @Autowired
    public AuditServiceImpl(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public List<Audit> getAllAuditLogs() {
        return auditRepository.findAll();
    }
}
