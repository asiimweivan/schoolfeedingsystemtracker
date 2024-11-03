package com.webtech.schoolfeedingsystemtracker.repositories;

import com.webtech.schoolfeedingsystemtracker.model.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    // You can define custom query methods if needed
}
