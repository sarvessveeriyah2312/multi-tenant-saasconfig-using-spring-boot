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

    @Cacheable(value = "tenantConfigs", key = "#tenantId")
    public List<TenantConfig> getConfigs(String tenantId) {
        System.out.println("Fetching from DB for tenant: " + tenantId);
        return repo.findByTenantId(tenantId);
    }

    @CachePut(value = "tenantConfigs", key = "#tenantId")
    public TenantConfig saveConfig(String tenantId, String key, Map<String, Object> value) {
        TenantConfig config = repo.findByTenantIdAndConfigKey(tenantId, key)
                .orElse(TenantConfig.builder()
                        .tenantId(tenantId)
                        .configKey(key)
                        .build());
        config.setConfigValue(value);
        return repo.save(config);
    }

    @CacheEvict(value = "tenantConfigs", key = "#tenantId")
    public void deleteConfig(String tenantId, String key) {
        repo.findByTenantIdAndConfigKey(tenantId, key)
                .ifPresent(repo::delete);
    }
}
