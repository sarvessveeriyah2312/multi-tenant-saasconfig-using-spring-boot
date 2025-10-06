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

    private final TenantConfigService service;

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<TenantConfig>> getConfigs(
            @PathVariable String tenantId,
            @RequestParam(defaultValue = "DEV") String env) {
        return ResponseEntity.ok(service.getConfigs(tenantId, env));
    }

    @PostMapping("/{tenantId}")
    public ResponseEntity<TenantConfig> saveConfig(
            @PathVariable String tenantId,
            @RequestParam String key,
            @RequestParam(defaultValue = "DEV") String env,
            @RequestBody Map<String, Object> value) {
        return ResponseEntity.ok(service.saveConfig(tenantId, env, key, value));
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteConfig(
            @PathVariable String tenantId,
            @RequestParam String key,
            @RequestParam(defaultValue = "DEV") String env) {
        service.deleteConfig(tenantId, env, key);
        return ResponseEntity.noContent().build();
    }
}
