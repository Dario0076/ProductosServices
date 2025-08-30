# Dockerfile para microservicio Spring Boot
FROM openjdk:17-jdk
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/ProductosService.jar"]
