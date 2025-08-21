# 🏦 Banking System Java Application

A **Core Banking System** built with **Java**, **Spring Boot**, **JWT Authentication**, and **PostgreSQL**, designed to demonstrate full-stack backend development skills for recruiters and portfolio.

---

## 🚀 Features

- User registration and login with **JWT authentication**
- Role-based access: `ADMIN` & `CUSTOMER`
- Account management:
  - Open accounts with initial deposit
  - Deposit / Withdraw
  - View account balance
- Money transfers between accounts
- Transaction history & account statements
- API documented using **Swagger/OpenAPI**
- Dockerized application with **PostgreSQL**
- CI/CD ready with **GitHub Actions**

---

## 💻 Tech Stack

- **Backend:** Java 17, Spring Boot 3
- **Security:** JWT, Spring Security
- **Database:** PostgreSQL (via JPA/Hibernate)
- **Build:** Maven
- **Containerization:** Docker & Docker Compose
- **Documentation:** Swagger
- **Testing:** JUnit

---

## 🏃 How to Run Locally

### Prerequisites:
- Java 17+
- Maven
- Docker & Docker Compose (optional)
- PostgreSQL (if running without Docker)

---

### Option 1: Using Docker
```bash
docker compose up --build

