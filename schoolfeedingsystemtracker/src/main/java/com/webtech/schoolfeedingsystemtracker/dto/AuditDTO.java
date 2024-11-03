package com.webtech.schoolfeedingsystemtracker.dto;

public class AuditDTO {
    private String action;
    private String entityName;
    private String user;

    // Default constructor
    public AuditDTO() {
    }

    // Parameterized constructor
    public AuditDTO(String action, String entityName, String user) {
        this.action = action;
        this.entityName = entityName;
        this.user = user;
    }

    // Getters and Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
