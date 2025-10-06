package com.saas.multitenantsaasconfig.service;
import com.saas.multitenantsaasconfig.config.TenantContext;
import com.saas.multitenantsaasconfig.model.AuditLog;
import com.saas.multitenantsaasconfig.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void record(String actionType, String entity, String details) {
        String tenantId = TenantContext.getTenantId();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        AuditLog log = AuditLog.builder()
                .tenantId(tenantId != null ? tenantId : "UNKNOWN")
                .username(username != null ? username : "SYSTEM")
                .actionType(actionType)
                .entityName(entity)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();

        auditLogRepository.save(log);
    }
}
