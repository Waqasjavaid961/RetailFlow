# RetailFlow Backend API

![Java](https://img.shields.io/badge/Java-17-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Hibernate](https://img.shields.io/badge/Hibernate-ORM-blue.svg)
![JWT](https://img.shields.io/badge/Security-JWT-yellow.svg)
![MySQL](https://img.shields.io/badge/Database-MySQL-lightgrey.svg)

**RetailFlow** is a robust, production-ready backend system designed for retail management. It provides a complete solution for managing stores, inventory, products, sales, and complex installment plans. The architecture strictly follows N-Tier design principles, emphasizing security, scalability, and clean code practices.

## 🚀 Key Features

*   **Multi-Store Management:** Role-based access control allowing users (Owners) to manage multiple store entities independently.
*   **Secure Authentication:** Stateless authentication implemented using Spring Security and JSON Web Tokens (JWT). Passwords are encrypted using BCrypt.
*   **Inventory Tracking:** Real-time stock management linked dynamically with product creation and sales.
*   **Point of Sale (POS):** Transactional processing of sales, supporting both immediate cash payments and structured installment plans.
*   **Installment Engine:** Advanced logic to calculate down payments, monthly installments, and track remaining balances securely.
*   **Centralized Context Handling:** Custom `ContextHolder` to securely extract authenticated user emails and active `storeId` directly from the `SecurityContext`.

## 🏗 Architecture & Design Patterns

*   **Layered Architecture:** Controller -> Service (Interface + Impl) -> Repository -> Model.
*   **DTO Pattern:** Strict separation of data transfer objects (Request/Response) from database entities to prevent over-posting and secure internal models.
*   **Mapping:** Utilizing **MapStruct** for compile-time, high-performance object mapping between Entities and DTOs.
*   **Helper Components:** Modularized helper classes (e.g., `StoreHelperMethod`, `SaleHelperMethod`) to keep business logic services lean and strictly focused.
*   **Global Exception Handling:** Centralized `@ControllerAdvice` to intercept custom exceptions and return consistent, structured JSON API responses (`ResponseApi<T>`).

## 🛡 Security Implementation

*   **Stateless Sessions:** Session creation policy set to `STATELESS`.
*   **Custom JWT Filter:** Intercepts requests, validates tokens, extracts the `storeId` claim, and builds the authentication context.
*   **CORS Configuration:** Explicitly configured CORS policy to handle cross-origin requests safely.
*   **Role-Based Access:** Endpoints are protected via `@PreAuthorize` and route matching (e.g., `/api/owner/**` requires `ROLE_owner`).

## 🛠 Tech Stack

*   **Core:** Java 17, Spring Boot 3
*   **Data Access:** Spring Data JPA, Hibernate
*   **Database:** MySQL
*   **Security:** Spring Security, JWT (jjwt)
*   **Utilities:** Lombok, MapStruct

## ⚙️ Local Setup Instructions

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/retailflow.git
    ```
2.  **Configure the Database:**
    *   Create a MySQL database named `retailflow`.
    *   Copy `src/main/resources/application.example.properties` to `src/main/resources/application.properties`.
    *   Update the database credentials and JWT secret key in `application.properties`.
3.  **Run the Application:**
    ```bash
    mvn spring-boot:run
    ```

## 📜 API Response Structure

All endpoints return a standardized wrapper:
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { ... },
  "localDateTime": "2026-07-02T10:00:00.000"
}
```

---
*Developed as a demonstration of production-grade backend engineering.*
