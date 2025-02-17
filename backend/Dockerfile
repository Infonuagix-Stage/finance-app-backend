# Utilisation d'une image officielle Java 21
FROM eclipse-temurin:21-jdk

# Définition du répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré par Maven
COPY target/*.jar app.jar

# Exposer le port sur lequel l'application écoute
EXPOSE 8080

# Commande pour exécuter l'application
CMD ["java", "-jar", "app.jar"]
