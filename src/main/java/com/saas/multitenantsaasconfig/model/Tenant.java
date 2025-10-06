package com.saas.multitenantsaasconfig.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tenants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String tenantId;     // unique identifier like "T001"

    @Column(nullable = false)
    private String name;         // company / tenant name

    @Column(nullable = false)
    private String contactEmail; // primary contact email

    @Column(nullable = false)
    private String status;       // ACTIVE, INACTIVE, SUSPENDED
}
