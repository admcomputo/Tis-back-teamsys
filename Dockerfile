# Paso 1: Construir la aplicación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the generated JAR directly using a generic naming convention wrapper
COPY --from=build /app/target/Tis-back-*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
