# Paso 1: Construir la aplicación
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .

# Run the build, then immediately list all files recursively to find the JAR
RUN mvn clean package -DskipTests && echo "--- CONTENIDO DEL DIRECTORIO TARGET ---" && ls -la target/

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy the generated JAR directly using a generic naming convention wrapper
COPY --from=build /app/target/Tis-back-*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
