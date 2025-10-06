package com.saas.multitenantsaasconfig.service;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.repository.TenantConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TenantConfigService {

    private final TenantConfigRepository repo;

    public List<TenantConfig> getConfigs(String tenantId) {
        return repo.findByTenantId(tenantId);
    }

    public TenantConfig saveConfig(String tenantId, String key, Map<String, Object> value) {
        TenantConfig config = repo.findByTenantIdAndConfigKey(tenantId, key)
                .orElse(TenantConfig.builder()
                        .tenantId(tenantId)
                        .configKey(key)
                        .build());
        config.setConfigValue(value);
        return repo.save(config);
    }

    public void deleteConfig(String tenantId, String key) {
        repo.findByTenantIdAndConfigKey(tenantId, key)
                .ifPresent(repo::delete);
    }
}
