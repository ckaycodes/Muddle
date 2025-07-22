FROM ubuntu:latest
LABEL authors="ckay"

# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jre-jammy

# Set the working directory in the container
WORKDIR /app

# Copy the jar built by Maven into the container
COPY target/Muddle-0.0.1-SNAPSHOT.jar app.jar

# Expose port your app listens on (usually 8080)
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]