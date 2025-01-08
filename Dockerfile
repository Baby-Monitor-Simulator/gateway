# Stage 1: Build the application
FROM openjdk:19-jdk-slim AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper, Maven settings, and pom.xml files
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Ensure the Maven wrapper is executable
RUN chmod +x ./mvnw

# Download dependencies to leverage Docker layer caching
RUN ./mvnw dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application and skip tests for faster builds
RUN ./mvnw clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:19-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/gateway_api-0.0.1-SNAPSHOT.jar /app/baby-monitor.jar

# Expose application port (if needed)
EXPOSE 8085

# Run the application
CMD ["java", "-jar", "/app/baby-monitor.jar"]
