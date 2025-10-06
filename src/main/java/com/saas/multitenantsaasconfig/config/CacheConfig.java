package com.saas.multitenantsaasconfig.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        // Define all cache names used in your app
        return new ConcurrentMapCacheManager(
                "tenantConfigList",
                "tenantConfigSingle"
        );
    }
}
