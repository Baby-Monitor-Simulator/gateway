# Use an OpenJDK image for Java applications
FROM openjdk:19-jdk-slim AS build

# Set the working directory in the container
WORKDIR /gateway-api

# Copy the Maven wrapper and pom.xml files
COPY mvnw pom.xml /gateway-api/
COPY .mvn /gateway-api/.mvn

# Check the contents of /gateway-api after copying
RUN echo "Contents of /gateway-api:" && ls -la /gateway-api

# Ensure Maven wrapper script is executable within the container
RUN chmod +x /gateway-api/mvnw

# Verify that mvnw is executable
RUN echo "Permissions of mvnw:" && ls -la /gateway-api/mvnw

# Install dependencies without running tests to leverage Docker layer caching
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src /gateway-api/src

# Package the application with Maven
RUN ./mvnw clean package -DskipTests

# List the contents of the target directory
RUN ls -la /gateway-api/target

# Start a new image to reduce the size of the final image
FROM openjdk:19-jdk-slim

# Set the working directory and copy the jar file from the build stage
WORKDIR /gateway-api
COPY --from=build /gateway-api/target/gateway_api-0.0.1-SNAPSHOT.jar /gateway-api/baby-monitor.jar

# Run the application
CMD ["java", "-jar", "/gateway-api/baby-monitor.jar"]
