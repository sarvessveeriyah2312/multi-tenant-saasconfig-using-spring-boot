package com.saas.multitenantsaasconfig.model;

import jakarta.persistence.*;
import lombok.*;

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConfigKey() {
        return configKey;
    }

    public void setConfigKey(String configKey) {
        this.configKey = configKey;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private String configKey;

    @Column(columnDefinition = "TEXT")
    private String configValue;
}