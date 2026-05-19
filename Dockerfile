FROM amazoncorretto:21-alpine
WORKDIR /app
COPY target/Lab_3_Fashion-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]