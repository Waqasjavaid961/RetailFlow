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
    
  
