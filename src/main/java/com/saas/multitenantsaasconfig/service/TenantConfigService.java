package com.saas.multitenantsaasconfig.service;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.repository.TenantConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TenantConfigService {

    private final TenantConfigRepository repo;

    // Cache only list results under a distinct cache name
    @Cacheable(value = "tenantConfigList", key = "#tenantId + '_' + #environment")
    public List<TenantConfig> getConfigs(String tenantId, String environment) {
        System.out.println("🧭 Fetching configs from DB for tenant: " + tenantId + " | env: " + environment);
        return repo.findByTenantIdAndEnvironment(tenantId, environment);
    }

    // Use different cache for single config objects
    @CachePut(value = "tenantConfigSingle", key = "#tenantId + '_' + #environment + '_' + #key")
    public TenantConfig saveConfig(String tenantId, String environment, String key, Map<String, Object> value) {
        TenantConfig config = repo.findByTenantIdAndConfigKeyAndEnvironment(tenantId, key, environment)
                .orElse(TenantConfig.builder()
                        .tenantId(tenantId)
                        .environment(environment)
                        .configKey(key)
                        .build());
        config.setConfigValue(value);
        TenantConfig saved = repo.save(config);

        // Evict list cache to ensure future GET gets fresh data
        evictTenantListCache(tenantId, environment);

        return saved;
    }

    // Use separate cache namespace for deletions
    @CacheEvict(value = "tenantConfigSingle", key = "#tenantId + '_' + #environment + '_' + #key")
    public void deleteConfig(String tenantId, String environment, String key) {
        repo.findByTenantIdAndConfigKeyAndEnvironment(tenantId, key, environment)
                .ifPresent(repo::delete);

        // Also clear the list cache so that next GET reloads from DB
        evictTenantListCache(tenantId, environment);
    }

    @CacheEvict(value = "tenantConfigList", key = "#tenantId + '_' + #environment")
    public void evictTenantListCache(String tenantId, String environment) {
        System.out.println("Cache cleared for tenant: " + tenantId + " | env: " + environment);
    }
}
