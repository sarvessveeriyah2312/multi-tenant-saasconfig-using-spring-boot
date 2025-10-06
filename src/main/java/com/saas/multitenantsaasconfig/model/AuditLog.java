package com.saas.multitenantsaasconfig.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String actionType;  // e.g. CREATE, UPDATE, DELETE

    @Column(nullable = false)
    private String entityName;  // e.g. TenantConfig, User, etc.

    @Column(columnDefinition = "TEXT")
    private String details;     // JSON or text payload of what changed

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
