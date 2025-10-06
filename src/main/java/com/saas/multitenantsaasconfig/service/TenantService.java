package com.saas.multitenantsaasconfig.service;

import com.saas.multitenantsaasconfig.model.Tenant;
import com.saas.multitenantsaasconfig.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantService {

    private final TenantRepository repo;

    public Tenant registerTenant(Tenant tenant) {
        if (repo.existsByTenantId(tenant.getTenantId())) {
            throw new IllegalArgumentException("Tenant ID already exists");
        }
        tenant.setStatus("ACTIVE");
        return repo.save(tenant);
    }

    public List<Tenant> listAllTenants() {
        return repo.findAll();
    }

    public Optional<Tenant> getTenantById(String tenantId) {
        return repo.findByTenantId(tenantId);
    }

    public void deleteTenant(String tenantId) {
        repo.findByTenantId(tenantId)
                .ifPresent(repo::delete);
    }

    public Tenant updateStatus(String tenantId, String status) {
        Tenant tenant = repo.findByTenantId(tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Tenant not found"));
        tenant.setStatus(status);
        return repo.save(tenant);
    }
}
