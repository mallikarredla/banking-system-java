# Bank Lite — Portfolio-Grade Core Banking (Spring Boot + JWT + PostgreSQL)

A realistic, production-style banking backend you can showcase on your resume, GitHub, and LinkedIn.

## Highlights
- Java 17, Spring Boot 3, RESTful API
- JWT Authentication (Stateless), Role-based access
- Domain: Users, Accounts, Transactions, Transfers, Statements
- PostgreSQL + JPA/Hibernate
- OpenAPI docs at `/swagger-ui.html`
- Docker Compose for DB + app
- CI with GitHub Actions
- Seed admin and demo users

## Quick Start

### 1) Run with Docker (recommended)
```bash
# from project root
docker compose up --build
```

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- DB: localhost:5432 (user: bank, password: bank, db: bank)

### 2) Run locally (requires PostgreSQL)
Update `src/main/resources/application.yml` if needed, then:
```bash
./mvnw spring-boot:run
```

## Auth Flow
1. `POST /api/auth/register` — create a user (CUSTOMER role).
2. `POST /api/auth/login` — obtain JWT.
3. Use `Authorization: Bearer <token>` for protected endpoints like:
   - `POST /api/accounts` (open account)
   - `POST /api/accounts/{id}/deposit`
   - `POST /api/accounts/{id}/withdraw`
   - `POST /api/transfers` (account-to-account)
   - `GET /api/accounts/{id}/statement`

Admin-only examples:
- `GET /api/admin/users`
- `GET /api/admin/accounts`

## Tech Stack
Spring Boot · Spring Security · JPA/Hibernate · PostgreSQL · JJWT · Bean Validation · Lombok · springdoc-openapi

## Portfolio Tips
- Write a case-study in this README (problem, design decisions, trade-offs).
- Add Postman collection + screenshots of Swagger and ERD.
- Create GitHub issues and a project board showing your planning.
- Record a 2–3 minute Loom demo walk-through.

## License
MIT
