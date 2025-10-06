package com.saas.multitenantsaasconfig.security;

import com.saas.multitenantsaasconfig.config.TenantContext;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantVerificationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        String jwtTenant = TenantContext.getTenantId();
        if (jwtTenant != null) {
            // Try tenantId from header or path
            String reqTenant = req.getHeader("X-Tenant-ID");
            if (reqTenant == null) {
                String uri = req.getRequestURI();
                String[] parts = uri.split("/");
                for (int i = 0; i < parts.length - 1; i++) {
                    if (parts[i].equalsIgnoreCase("configs")) {
                        reqTenant = parts[i + 1];
                        break;
                    }
                }
            }

            if (reqTenant != null && !reqTenant.equals(jwtTenant)) {
                res.setStatus(HttpStatus.FORBIDDEN.value());
                res.getWriter().write("Tenant mismatch. Access denied.");
                return;
            }
        }
        chain.doFilter(req, res);
    }
}

