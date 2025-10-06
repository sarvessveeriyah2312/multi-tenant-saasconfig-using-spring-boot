Here’s a **complete, professional `README.md`** for your **Multi-Tenant SaaS Configuration Backend** project — covering setup, structure, endpoints, and key features.

---

# 🏢 Multi-Tenant SaaS Configuration Backend

This project is a **Spring Boot 3.5.x** backend service designed to manage **multi-tenant configurations** for SaaS platforms.
It supports secure authentication, tenant registration, configuration management, and centralized exception handling.

---

## 🚀 Features

* **Multi-Tenant Management**

    * Register, list, update, and delete tenants
    * Tenant status lifecycle (ACTIVE / INACTIVE)
    * Unique `tenant_id` enforced by database constraint

* **Configuration Management**

    * Store tenant-specific key–value JSON configurations
    * REST endpoints for CRUD operations

* **Security**

    * JWT-based authentication filter
    * Tenant verification filter (isolates per-tenant access)
    * Role-based access (`ADMIN`, `STAFF`, etc.)

* **Error Handling**

    * Global exception handler with clean JSON error responses
    * Graceful handling of SQL integrity violations and validation errors

* **Cross-Origin Access**

    * CORS enabled for Angular frontend (`http://localhost:4200`)

---

## 🧩 Tech Stack

| Layer           | Technology                           |
| --------------- | ------------------------------------ |
| **Language**    | Java 21                              |
| **Framework**   | Spring Boot 3.5.x                    |
| **Database**    | PostgreSQL                           |
| **Security**    | Spring Security 6 + JWT              |
| **ORM**         | Hibernate / Spring Data JPA          |
| **Build Tool**  | Maven                                |
| **Frontend**    | Angular 18 (Tenant Config Dashboard) |
| **Server Port** | `8081`                               |

---

## 🧱 Project Structure

```
multi-tenant-saasconfig/
 ├── src/
 │   ├── main/java/com/saas/multitenantsaasconfig/
 │   │   ├── controller/
 │   │   │   ├── TenantController.java
 │   │   │   └── TenantConfigController.java
 │   │   ├── model/
 │   │   │   ├── Tenant.java
 │   │   │   └── TenantConfig.java
 │   │   ├── service/
 │   │   │   ├── TenantService.java
 │   │   │   └── TenantConfigService.java
 │   │   ├── security/
 │   │   │   ├── JwtAuthenticationFilter.java
 │   │   │   ├── TenantVerificationFilter.java
 │   │   │   └── SecurityConfig.java
 │   │   ├── exception/
 │   │   │   └── GlobalExceptionHandler.java
 │   │   └── config/
 │   │       └── CorsConfig.java
 │   └── resources/
 │       ├── application.yml
 │       └── schema.sql
 ├── pom.xml
 └── README.md
```

---

## ⚙️ Configuration

### **application.yml**

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/multitenant
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate.format_sql: true
  main:
    allow-bean-definition-overriding: true

server:
  port: 8081

logging:
  level:
    org.hibernate.SQL: warn
    org.springframework.web: info
```

---

## 🧠 Key Components

### 🔐 `SecurityConfig.java`

```java
http
    .csrf(csrf -> csrf.disable())
    .authorizeHttpRequests(auth -> auth
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers("/api/tenant-config/**").permitAll()
        .anyRequest().authenticated()
    )
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
    .addFilterAfter(new TenantVerificationFilter(), JwtAuthenticationFilter.class);
```

✅ Allows open access for config endpoints while maintaining JWT protection elsewhere.

---

### 🧾 `GlobalExceptionHandler.java`

Gracefully handles database and validation errors:

```json
{
  "status": "error",
  "message": "Data integrity violation: contact_email cannot be null"
}
```

---

## 📡 API Endpoints

### **Tenant Management**

| Method   | Endpoint                                       | Description           |
| -------- | ---------------------------------------------- | --------------------- |
| `POST`   | `/api/tenants`                                 | Register a new tenant |
| `GET`    | `/api/tenants`                                 | List all tenants      |
| `GET`    | `/api/tenants/{tenantId}`                      | Get tenant details    |
| `PATCH`  | `/api/tenants/{tenantId}/status?status=ACTIVE` | Update tenant status  |
| `DELETE` | `/api/tenants/{tenantId}`                      | Delete a tenant       |

**Example Request (POST)**

```json
{
  "tenantId": "tenant1",
  "name": "Acme Corp",
  "contactEmail": "admin@acme.com",
  "status": "ACTIVE"
}
```

---

### **Tenant Configuration**

| Method   | Endpoint                    | Description                 |
| -------- | --------------------------- | --------------------------- |
| `POST`   | `/api/tenant-config/create` | Create tenant configuration |
| `GET`    | `/api/tenant-config/list`   | List all configurations     |
| `GET`    | `/api/tenant-config/{id}`   | Get configuration by ID     |
| `PUT`    | `/api/tenant-config/{id}`   | Update configuration        |
| `DELETE` | `/api/tenant-config/{id}`   | Delete configuration        |

**Example Request**

```json
{
  "tenantId": "tenant1",
  "description": "App theme configuration",
  "config": {
    "theme": "dark",
    "maxUsers": 100
  }
}
```

---

## 🧪 Testing with Postman

1. **Base URL:** `http://localhost:8081`
2. Set `Content-Type: application/json`
3. Example for tenant creation:

   ```json
   {
     "tenantId": "tenant1",
     "name": "Acme Corp",
     "contactEmail": "admin@acme.com",
     "status": "ACTIVE"
   }
   ```
4. Example for configuration creation:

   ```json
   {
     "tenantId": "tenant1",
     "description": "Default settings",
     "config": {
       "timezone": "Asia/Kuala_Lumpur",
       "language": "en"
     }
   }
   ```

---

## 🧩 Example Responses

**✅ Success**

```json
{
  "id": 1,
  "tenantId": "tenant1",
  "description": "Default settings",
  "config": {
    "timezone": "Asia/Kuala_Lumpur",
    "language": "en"
  }
}
```

**❌ Error**

```json
{
  "status": "error",
  "message": "Data integrity violation: contact_email cannot be null"
}
```

---

## 🧰 Run Locally

### **Build & Run**

```bash
mvn clean install
mvn spring-boot:run
```

### **Database**

Make sure PostgreSQL is running:

```bash
psql -U postgres -d multitenant
```

---

## 🧱 Future Enhancements

* ✅ Add role-based access (Admin vs Tenant User)
* ✅ Enable dynamic schema creation per tenant
* ✅ Integrate centralized audit logging
* ✅ Add pagination and filtering on list endpoints

---

## 👨‍💻 Author

**Sarvess Veeriyah**
Full-Stack Developer & AI Engineer
🌐 [https://sarvessveeriyah.vercel.app](https://sarvessveeriyah.vercel.app)

---