package com.saas.multitenantsaasconfig.service;


import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.repository.TenantConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TenantConfigService {

    private final TenantConfigRepository tenantConfigRepository;

    public List<TenantConfig> getConfigs(String tenantId) {
        return tenantConfigRepository.findByTenantId(tenantId);
    }

    public TenantConfig saveConfig(String tenantId, String key, String value) {
        TenantConfig config = tenantConfigRepository.findByTenantIdAndConfigKey(tenantId, key)
                .orElse(TenantConfig.builder().tenantId(tenantId).configKey(key).build());
        config.setConfigValue(value);
        return tenantConfigRepository.save(config);
    }

    public void deleteConfig(String tenantId, String key) {
        tenantConfigRepository.findByTenantIdAndConfigKey(tenantId, key)
                .ifPresent(tenantConfigRepository::delete);
    }
}