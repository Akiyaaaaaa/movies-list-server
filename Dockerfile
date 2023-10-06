FROM maven:3.8.6-jdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=build /target/movies-review-0.0.1-SNAPSHOT.jar movies-review-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/movies-review-0.0.1.jar"]
