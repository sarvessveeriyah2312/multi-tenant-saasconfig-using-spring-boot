package com.saas.multitenantsaasconfig.controller;

import com.saas.multitenantsaasconfig.model.TenantConfig;
import com.saas.multitenantsaasconfig.service.TenantConfigService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/config")
public class TenantConfigController {

    private final TenantConfigService service;

    public TenantConfigController(TenantConfigService service) {
        this.service = service;
    }

    @GetMapping("/{tenantId}")
    public ResponseEntity<List<TenantConfig>> getConfigs(@PathVariable String tenantId) {
        return ResponseEntity.ok(service.getConfigs(tenantId));
    }

    @PostMapping("/{tenantId}")
    public ResponseEntity<TenantConfig> saveConfig(
            @PathVariable String tenantId,
            @RequestParam String key,
            @RequestParam String value) {
        return ResponseEntity.ok(service.saveConfig(tenantId, key, value));
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteConfig(
            @PathVariable String tenantId,
            @RequestParam String key) {
        service.deleteConfig(tenantId, key);
        return ResponseEntity.noContent().build();
    }
}