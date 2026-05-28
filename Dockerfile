FROM maven:3.9.6-eclipse-temurin-17
ENV LANG=C.UTF-8

WORKDIR /app

# Copy the project files and build the jar file
COPY . .
RUN mvn clean package -DskipTests

# Expose the application port
EXPOSE 8081

# Execute the application directly from the target folder
ENTRYPOINT ["sh", "-c", "java -jar target/*.jar"]
