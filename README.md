<!-- Title -->

<h1 align="center">RetailFlow</h1>

<p align="center">
A Backend Retail Management System
</p>

---

## Introduction

RetailFlow is a backend retail management system that helps retail stores manage products, inventory, customers, sales, installment plans, and installment payments. The project is designed to simplify daily store operations by keeping retail data organized in one place.

---

## Features

### Authentication & Authorization

- Secure authentication using JWT.
- Role-based access control using Spring Security.

### Store Management

- Create, update, delete, and manage multiple stores.
- Generate a new JWT token when switching between stores (Multi-Tenant JWT).

### Product Management

- Add, update, delete, and view products.
- Filter products by category.

### Inventory Management

- Increase stock.
- Decrease stock.
- View current inventory.

### Customer Management

- Manage customer information.
- Support both walk-in customers and installment customers.

### Sales Management

- Create sales.
- Process refunds.
- View sales history.

### Installment Management

- Create installment plans.
- Record installment payments.
- View installment history and remaining balance.

# Tech Stack
### Backend
- java 21
- spring boot
- spring security
- spring data jpa
### DataBase
- MySQL
### Authentication
-Jwt and mutli-tent Jwt
### Build Tools
- Maven
### Utilities
- lambok
- mapStruct
### Testing
- Postman
### Version Control
- Git & GitHub
  
### Architecture
-  Controller
-     |
-   Service
-     |
-  Repositery
-     |
-  Database

### Controller
- controller handle Http Request only
### Service
- service handles only business logic
### Repositery
- communicate with  database 
### Database
- save application data
  ### ERD Diagram
  - Entity relationship diagram
  - the ERD diagram represent the database structure of retail flow
  - here is the link
<p align="center">
  🖼️ <b><a href="https://raw.githubusercontent.com/Waqasjavaid961/RetailFlow/main/ERD%20DIAGRAM/Untitled.svg" target="_blank">Click Here to View Full ERD Image</a></b>
</p>
# API Endpoints

The project is organized into different modules. Each module provides REST APIs for a specific business operation.

| Module | Description |
|---------|-------------|
| Authentication | Register users, login, and generate JWT tokens. |
| Store | Create, update, delete, and manage multiple stores. |
| Product | Add products, update product details, delete products, and search by category. |
| Inventory | Increase stock, decrease stock, and view available inventory. |
| Customer | Store customer information for cash and installment purchases. |
| Sale | Create sales, process refunds, and view sales history. |
| Installment Plan | Create installment plans and view installment details. |
| Installment Payment | Record installment payments and view payment history. | 

- this part was a generated but i understand the flow

---

# Business Flow

The application follows a simple business workflow to keep data consistent and avoid invalid operations.

### Sale Creation

- Validate the selected store.
- Validate every product in the request.
- Check available inventory.
- Reduce product stock.
- Create the sale and sale items.
- Save customer information if provided.
- If the payment type is **Installment**, automatically create an installment plan.

### Installment Payment

- Verify the customer.
- Verify the sale belongs to the customer.
- Find the related installment plan.
- Validate the payment amount.
- Update the remaining balance.
- Save the payment record.
- Mark the installment as **Completed** when the remaining amount becomes zero.

---

# Authentication Flow

RetailFlow uses Spring Security with JWT authentication.

1. User logs in with email and password.
2. Server verifies the credentials.
3. A JWT token is generated.
4. The client includes the token in every protected request.
5. Spring Security validates the token before allowing access to the requested endpoint.

---

# Folder Structure

The project follows a layered architecture to keep responsibilities separated.

```text
src
 └── main
      └── java
           └── com.retailflow.retailflow
                ├── controller
                ├── service
                ├── repository
                ├── model
                ├── dto
                ├── mapper
                ├── helper
                ├── security
                ├── common
                ├── enums
                └── exceptions
```

Each package has a single responsibility, making the project easier to understand and maintain.

---

# Getting Started

### Clone the repository

```bash
git clone https://github.com/your-username/RetailFlow.git
```

### Create a MySQL database

Create a new database and update the database configuration inside `application.properties`.

### Run the project

```bash
mvn spring-boot:run
```

The application will start on the default Spring Boot port.

---

# Future Improvements

Some features are planned for future versions of the project.

- Docker support
- Unit and integration testing
- Redis caching
- Email notifications
- Sales reports
- Dashboard and analytics
- Audit logging

---

# Developer

**Waqas Javaid**

Java Backend Developer

## Contact

- 📧 Email: mailto:bhalliwaqas0@gmail.com
- 💼 LinkedIn: https://www.linkedin.com/in/waqas-javaid-b738533aa/
- 💻 GitHub: https://github.com/Waqasjavaid961

This project was built as a learning project to improve backend development skills and understand real-world business workflows using Spring Boot.
    
  
