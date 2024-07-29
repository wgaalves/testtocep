FROM amazoncorretto:21

ADD ./build/libs/cep-service-0.0.1-SNAPSHOT.jar /app/app.jar
CMD ["java", "-Xmx200m", "-jar", "/app/app.jar"]

EXPOSE 8080