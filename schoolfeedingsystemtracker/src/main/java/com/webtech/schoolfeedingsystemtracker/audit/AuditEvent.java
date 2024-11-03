package com.webtech.schoolfeedingsystemtracker.audit;

import java.time.LocalDateTime;

public class AuditEvent {
    private final String entityName;
    private final String action;
    private final String user;
    private final LocalDateTime timestamp;

    public AuditEvent(String entityName, String action, String user, LocalDateTime timestamp) {
        this.entityName = entityName;
        this.action = action;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getAction() {
        return action;
    }

    public String getUser() {
        return user;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
