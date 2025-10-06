package com.saas.multitenantsaasconfig.model;

import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Map;

@Entity
@Table(name = "tenant_configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to tenant by tenantId (not foreign key — safer for simple SaaS)
    @Column(nullable = false)
    private String tenantId;

    // Key for config item (e.g., "emailSettings", "paymentGateway", etc.)
    @Column(nullable = false)
    private String configKey;

    // Use JSONB type to store structured configuration
    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> configValue;
}
