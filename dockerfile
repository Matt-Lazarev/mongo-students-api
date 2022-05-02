FROM alpine

WORKDIR /app

RUN apk add openjdk17

COPY target/mongo-students-api.jar /app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]