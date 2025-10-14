FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package && mv target/*.jar target/app.jar

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/app.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]