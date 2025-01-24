# Use an official OpenJDK runtime as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /journalApp

# Copy the uploaded JAR file into the container
COPY journalApp-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (e.g., 8080)
EXPOSE 8080

# Set the default command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
