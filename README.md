# Finance Data Processing and Access Control

## Tech Stack
- Java 21
- Spring Boot 4.0.3
- Spring Security + JWT
- MySQL 8
- Swagger UI (SpringDoc OpenAPI)

## How to Run

### Prerequisites
- Java 21
- MySQL 8 running on localhost:3306

### Environment Variables Required
Set these before running:

| Variable | Description |
|---|---|
| DB_USERNAME | MySQL username |
| DB_PASSWORD | MySQL password |
| JWT_SECRET | Secret key for JWT |
| JWT_EXPIRATION | Token expiry in ms (86400000 = 24hrs) |

### Steps
1. Clone the repository
2. Set environment variables
3. Run as Spring Boot App
4. Open http://localhost:8080/swagger-ui/index.html

## Default Test Users (Auto-created on startup)

| Email | Password | Role |
|---|---|---|
| admin@finance.com | Admin@123 | ADMIN |
| analyst@finance.com | Analyst@123 | ANALYST |
| viewer@finance.com | Viewer@123 | VIEWER |

## API Endpoints

### Auth (Public)
- POST /api/auth/register
- POST /api/auth/login

### Financial Records
- GET    /api/records          → All roles
- POST   /api/records          → ANALYST, ADMIN
- PUT    /api/records/{id}     → ANALYST, ADMIN
- DELETE /api/records/{id}     → ADMIN only

### Dashboard
- GET /api/dashboard/summary      → All roles
- GET /api/dashboard/by-category  → All roles
- GET /api/dashboard/trends       → All roles
- GET /api/dashboard/recent       → All roles

### User Management
- GET    /api/users                    → ADMIN only
- PUT    /api/users/{id}/role          → ADMIN only
- PUT    /api/users/{id}/toggle-status → ADMIN only
- DELETE /api/users/{id}               → ADMIN only

## Role Permissions

| Action | VIEWER | ANALYST | ADMIN |
|---|---|---|---|
| View records | ✅ | ✅ | ✅ |
| Create records | ❌ | ✅ | ✅ |
| Edit records | ❌ | ✅ | ✅ |
| Delete records | ❌ | ❌ | ✅ |
| View dashboard | ✅ | ✅ | ✅ |
| Manage users | ❌ | ❌ | ✅ |

## Design Pattern
Layered Architecture:
- Controller → receives requests
- Service → business logic
- Repository → database operations

## Assumptions
- Anyone can register with any role (for demo purposes)
- Soft delete used for financial records
- JWT token expires in 24 hours
