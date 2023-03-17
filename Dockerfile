FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-al2-jdk
WORKDIR /app
COPY --from=builder /target/event-app-0.0.1-SNAPSHOT.jar event-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "event-app-0.0.1-SNAPSHOT.jar"]