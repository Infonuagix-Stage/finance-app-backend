# 🏦 Finance App - Backend  

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.0-green)  
A backend API for managing personal finance data, including authentication, transactions, and budgeting.  

## 📋 Table of Contents  

- [Features](#-features)  
- [Technologies](#-technologies)  
- [Installation](#-installation)  
- [Configuration](#-configuration)  
- [Running the Application](#-running-the-application)  
- [Contributing](#-contributing)  
- [License](#-license)  

## 🚀 Features  

- **User Authentication** (JWT-based)  
- **Transaction Management** (CRUD operations)  
- **Budget Tracking**  
- **Financial Reports** (income vs expenses analysis)  
- **RESTful API** (secured endpoints)  

## 💻 Technologies  

- **Backend**:  
  - Spring Boot 3  
  - Spring Security (JWT Authentication)  
  - Spring Data JPA (Hibernate)  
  - MySQL (or PostgreSQL)  
- **Tools**:  
  - Maven  
  - Lombok  
  - Docker (optional)  

## 👥 Installation  

1. Clone the repository:  

   ```bash
   git clone https://github.com/your-username/finance-app-backend.git
   cd finance-app-backend
   ```

2. Install dependencies:  

   ```bash
   mvn clean install
   ```

## ⚙️ Configuration  

1. Create a database:  

   ```sql
   CREATE DATABASE finance_app;
   ```

2. Configure `application.properties` (or `application.yml`):  

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/finance_app
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Create a .env file at the root:
```env
SECRET_KEY= (generate a 64 bits key)
FRONTEND_URL=http://localhost:3000
```

## ▶️ Running the Application  

### With Maven  

```bash
mvn spring-boot:run
```

### With a JAR file  

```bash
java -jar target/finance-app-backend.jar
```

### Testing API  
  
- **API Base URL**: [`http://localhost:8080/api`](http://localhost:8080)  

## 🤝 Contributing  

1. Create a feature branch:  

   ```bash
   git checkout -b feature/new-feature
   ```

2. Commit changes:  

   ```bash
   git commit -m "feat: add new feature"
   ```

3. Push the branch:  

   ```bash
   git push origin feature/new-feature
   ```

## ✨ Developed by Mehdi Ben Nasrallah and Guillaume Fournier - 2025  

Contact: mehdi.ben3@hotmail.com  

