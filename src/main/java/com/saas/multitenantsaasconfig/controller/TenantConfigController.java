package com.saas.multitenantsaasconfig.controller;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.service.TenantConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/configs")
@RequiredArgsConstructor
public class TenantConfigController {

    private final TenantConfigService tenantConfigService;

    @GetMapping("/{tenantId}/{environment}")
    public ResponseEntity<List<TenantConfig>> getConfigs(
            @PathVariable String tenantId,
            @PathVariable String environment) {
        return ResponseEntity.ok(tenantConfigService.getConfigs(tenantId, environment));
    }

    @PostMapping("/{tenantId}/{environment}")
    public ResponseEntity<TenantConfig> saveConfig(
            @PathVariable String tenantId,
            @PathVariable String environment,
            @RequestParam String key,
            @RequestBody Map<String, Object> payload) {  // ✅ Correct type
        TenantConfig config = tenantConfigService.saveConfig(tenantId, environment, key, payload);
        return ResponseEntity.ok(config);
    }

    @DeleteMapping("/{tenantId}/{environment}")
    public ResponseEntity<Void> deleteConfig(
            @PathVariable String tenantId,
            @PathVariable String environment,
            @RequestParam String key) {
        tenantConfigService.deleteConfig(tenantId, environment, key);
        return ResponseEntity.noContent().build();
    }
}
