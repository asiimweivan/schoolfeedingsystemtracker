package com.webtech.schoolfeedingsystemtracker.audit;

import com.webtech.schoolfeedingsystemtracker.model.Audit;

import java.util.List;

/**
 * Interface for auditing logs.
 */
public interface AuditInterface {

    /**
     * Retrieve all audit logs.
     *
     * @return a list of audit logs.
     */
    List<Audit> getAllAuditLogs();
}
