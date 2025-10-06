package com.saas.multitenantsaasconfig.repository;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long> {
    List<TenantConfig> findByTenantId(String tenantId);
    Optional<TenantConfig> findByTenantIdAndConfigKey(String tenantId, String configKey);
}