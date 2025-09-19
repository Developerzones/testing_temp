# Step 1: Build the JAR using Gradle
FROM gradle:8.7-jdk21 AS build
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project
RUN gradle bootJar --no-daemon

# Step 2: Run the built JAR
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /home/gradle/project/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
