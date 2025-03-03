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
  - PostgreSQL
- **Tools**:  
  - Maven  
  - Docker 

## 👥 Installation  

1. Clone the repository:  

   ```bash
   git clone https://github.com/Infonuagix-Stage/finance-app-backend.git
   cd backend
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

2. Configure `application.properties` and make sure the environment variables are assigned to some values:  

   ```properties
   spring.datasource.url=${DATABASE_URL}
   spring.datasource.username=${DATABASE_USERNAME}
   spring.datasource.password=${DATABASE_PASSWORD}
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

