package com.webtech.schoolfeedingsystemtracker.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

@Component
public class AuditListener {

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public AuditListener(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @PrePersist
    public void onPrePersist(Object object) {
        publishAuditEvent(object, "CREATE");
    }

    @PreUpdate
    public void onPreUpdate(Object object) {
        publishAuditEvent(object, "UPDATE");
    }

    @PreRemove
    public void onPreRemove(Object object) {
        publishAuditEvent(object, "DELETE");
    }

    private void publishAuditEvent(Object object, String action) {
        String user = getCurrentUsername();
        AuditEvent auditEvent = new AuditEvent(object.getClass().getSimpleName(), action, user, LocalDateTime.now());
        eventPublisher.publishEvent(auditEvent);
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null && authentication.isAuthenticated()) ? authentication.getName() : "anonymous";
    }
}
