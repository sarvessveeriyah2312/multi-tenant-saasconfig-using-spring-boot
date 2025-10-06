package com.saas.multitenantsaasconfig.controller;

import com.saas.multitenantsaasconfig.model.AuditLog;
import com.saas.multitenantsaasconfig.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAll() {
        return auditLogRepository.findAll();
    }
}
