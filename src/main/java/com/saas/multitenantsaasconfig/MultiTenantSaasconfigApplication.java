package com.saas.multitenantsaasconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MultiTenantSaasconfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiTenantSaasconfigApplication.class, args);
    }

}
