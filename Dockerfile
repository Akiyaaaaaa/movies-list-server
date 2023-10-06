FROM openjdk:8-jdk-alpine
EXPOSE 8080
COPY target/movies-review-0.0.1-SNAPSHOT.jar movies-review-0.0.1.jar
ENTRYPOINT ["java", "-jar", "/movies-review-0.0.1.jar"]