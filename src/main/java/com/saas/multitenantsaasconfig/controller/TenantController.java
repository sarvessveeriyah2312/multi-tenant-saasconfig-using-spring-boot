package com.saas.multitenantsaasconfig.controller;

import com.saas.multitenantsaasconfig.model.Tenant;
import com.saas.multitenantsaasconfig.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService service;


    @PostMapping
    public ResponseEntity<Tenant> registerTenant(@RequestBody Tenant tenant) {
        return ResponseEntity.ok(service.registerTenant(tenant));
    }


    @GetMapping
    public ResponseEntity<List<Tenant>> listTenants() {
        return ResponseEntity.ok(service.listAllTenants());
    }


    @GetMapping("/{tenantId}")
    public ResponseEntity<Tenant> getTenant(@PathVariable String tenantId) {
        return service.getTenantById(tenantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{tenantId}")
    public ResponseEntity<Void> deleteTenant(@PathVariable String tenantId) {
        service.deleteTenant(tenantId);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{tenantId}/status")
    public ResponseEntity<Tenant> updateStatus(@PathVariable String tenantId,
                                               @RequestParam String status) {
        return ResponseEntity.ok(service.updateStatus(tenantId, status));
    }
}
