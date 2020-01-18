FROM openjdk:8-jdk-alpine
WORKDIR /tmp

COPY mvnw /tmp/
COPY .mvn /tmp/
COPY pom.xml /tmp/
COPY src src

RUN ./mvnw clean install

ENTRYPOINT ./mvnw spring-boot:run
