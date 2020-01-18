FROM openjdk:8-jdk-alpine
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml /pom.xml
COPY src src

RUN ./mvnw clean install

ENTRYPOINT ./mvnw spring-boot:run
