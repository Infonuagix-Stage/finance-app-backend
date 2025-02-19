# Dockerfile (à la racine du dépôt)

# Étape 1 : Build (Maven + Java 17)
FROM maven:3.9.4-eclipse-temurin-17 AS builder
WORKDIR /app
# Copier le pom.xml et le dossier src depuis /backend
COPY backend/pom.xml .
COPY backend/src ./src
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Étape 2 : Runtime (Java 17)
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
