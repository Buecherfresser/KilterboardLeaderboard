# Start with a base image that has Java installed
FROM openjdk:21-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot jar file to the container
COPY target/KilterBoardLeaderboard-0.0.1-SNAPSHOT.jar /app/KilterBoardLeaderboard-0.0.1-SNAPSHOT.jar

# Expose the port your Spring Boot application will run on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "KilterBoardLeaderboard-0.0.1-SNAPSHOT.jar"]

# Give read/write access to files (example for /app/data directory)
RUN mkdir /app/data && chmod -R 777 /app/data
