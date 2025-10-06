package com.saas.multitenantsaasconfig.repository;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantConfigRepository extends JpaRepository<TenantConfig, Long> {

    List<TenantConfig> findByTenantId(String tenantId);

    Optional<TenantConfig> findByTenantIdAndConfigKey(String tenantId, String configKey);

    void deleteByTenantIdAndConfigKey(String tenantId, String configKey);
}
