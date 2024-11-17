FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
COPY src src
RUN ./gradlew clean build -x test
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
COPY .env /app/.env
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
