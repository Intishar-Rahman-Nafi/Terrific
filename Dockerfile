# Use an official OpenJDK 22 runtime with slim variant as a parent image
FROM openjdk:22-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build file and the source code
COPY pom.xml .
COPY src ./src

# Install Maven
RUN apt-get update && apt-get install -y maven

# Copy the .env file
COPY .env .
ENV cat .env | xargs

# Package the application
RUN mvn clean package -DskipTests

# Expose the port the application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/Terriffic-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]