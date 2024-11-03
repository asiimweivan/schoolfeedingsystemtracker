package com.webtech.schoolfeedingsystemtracker.audit;

import com.webtech.schoolfeedingsystemtracker.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditEventListener {

    private final AuditService auditService;

    @Autowired
    public AuditEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @EventListener
    public void handleAuditEvent(AuditEvent event) {
        auditService.logAction(event.getAction(), event.getEntityName(), event.getUser());
    }
}
