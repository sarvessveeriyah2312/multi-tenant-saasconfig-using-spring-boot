package com.saas.multitenantsaasconfig.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.repository.TenantConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TenantConfigService {

    private final TenantConfigRepository tenantConfigRepository;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Cacheable(value = "tenantConfigList", key = "#tenantId + '_' + #environment")
    public List<TenantConfig> getConfigs(String tenantId, String environment) {
        auditLogService.record("READ", "TenantConfig", "Fetched configs for " + tenantId);
        return tenantConfigRepository.findByTenantId(tenantId);
    }

    @CachePut(value = "tenantConfigSingle", key = "#tenantId + '_' + #environment + '_' + #key")
    public TenantConfig saveConfig(String tenantId, String environment, String key, Object payload) {
        try {
            Map<String, Object> configMap;

            // Convert payload to Map<String, Object>
            if (payload instanceof Map<?, ?> mapPayload) {
                // Safe conversion from Map<?, ?> to Map<String, Object>
                configMap = new HashMap<>();
                for (Map.Entry<?, ?> entry : mapPayload.entrySet()) {
                    configMap.put(entry.getKey().toString(), entry.getValue());
                }
            } else if (payload instanceof String strPayload) {
                try {
                    // Try to parse string as JSON
                    configMap = objectMapper.readValue(strPayload, new TypeReference<Map<String, Object>>() {});
                } catch (Exception e) {
                    // If not valid JSON, treat as simple value
                    configMap = Map.of("value", strPayload);
                }
            } else {
                // For other object types, wrap in a map
                configMap = Map.of("value", payload);
            }

            TenantConfig config = TenantConfig.builder()
                    .tenantId(tenantId)
                    .configKey(key)
                    .configValue(configMap) // Directly use Map, not JSON string
                    .build();

            TenantConfig saved = tenantConfigRepository.save(config);
            auditLogService.record("CREATE", "TenantConfig", "Created key: " + key + " for " + tenantId);
            return saved;

        } catch (Exception e) {
            throw new RuntimeException("Failed to process config payload", e);
        }
    }

    @CacheEvict(value = "tenantConfigSingle", key = "#tenantId + '_' + #environment + '_' + #key")
    public void deleteConfig(String tenantId, String environment, String key) {
        tenantConfigRepository.deleteByTenantIdAndConfigKey(tenantId, key);
        auditLogService.record("DELETE", "TenantConfig", "Deleted key: " + key + " for " + tenantId);
    }
}