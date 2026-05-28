# Paso 1: Construir la aplicación
FROM maven:3.9.6-eclipse-temurin-17 AS build
ENV LANG=C.UTF-8
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jdk-jammy
# Enforcing the check inside the exact absolute build directory path
COPY --from=build /artifacts/build/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
