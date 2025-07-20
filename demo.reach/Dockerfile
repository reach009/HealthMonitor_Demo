# Stage 1: Use a base image with Java 21 for a consistent build environment
FROM openjdk:21-jdk-slim as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the rest of the application source code
COPY . .

# Grant execute permission to the Maven wrapper
RUN chmod +x ./mvnw

# Build the application using Maven, skipping tests as they are run in the CI pipeline
RUN ./mvnw -B package -DskipTests

# Stage 2: Create a minimal final image for production
FROM openjdk:21-jdk-slim

# Set the working directory for the final application
WORKDIR /app

# Copy only the built .jar file from the builder stage
# This makes the final image smaller and more secure
COPY --from=builder /app/target/*.jar app.jar

# Expose the port that the Spring Boot application runs on
EXPOSE 8080

# The command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]