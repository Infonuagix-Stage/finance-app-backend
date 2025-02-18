# Étape 1 : build
FROM maven:3.8.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests

# Étape 2 : runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /backend/target/*.jar backend-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
