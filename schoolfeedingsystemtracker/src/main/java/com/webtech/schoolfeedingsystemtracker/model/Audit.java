package com.webtech.schoolfeedingsystemtracker.model;

import jakarta.persistence.*;

import jakarta.persistence.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "audit_trail")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String entityName;
    private String user;

    private LocalDateTime timestamp;

    @Transient // This annotation is important to indicate that this field should not be stored in the database
    private String formattedTimestamp;

    public Audit() {
        this.timestamp = LocalDateTime.now();
    }

    public Audit(String action, String entityName, String user) {
        this.action = action;
        this.entityName = entityName;
        this.user = user;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getFormattedTimestamp() {
        return formatDate(timestamp);
    }

    // Formatting the timestamp to a readable string
    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    @Override
    public String toString() {
        return "Audit{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", entityName='" + entityName + '\'' +
                ", user='" + user + '\'' +
                ", timestamp=" + timestamp +
                ", formattedTimestamp='" + getFormattedTimestamp() + '\'' + // Display formatted timestamp
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Audit)) return false;
        Audit audit = (Audit) o;
        return id != null && id.equals(audit.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
