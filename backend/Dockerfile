# Use OpenJDK as the base image
FROM eclipse-temurin:23-alpine

WORKDIR /app

# Copy the JAR file
COPY target/*.war app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
