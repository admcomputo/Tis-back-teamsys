# Paso 1: Construir la aplicación
FROM maven:3.9.6-eclipse-temurin-17 AS build
ENV LANG=C.UTF-8
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Paso 2: Ejecutar la aplicación
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# This dynamic find command locates any generated jar file from the build stage 
# and safely copies it right into our runtime working directory as app.jar
COPY --from=build /app/**/target/*.jar ./app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
