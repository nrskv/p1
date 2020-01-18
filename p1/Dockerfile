FROM openjdk:8-jdk-alpine
WORKDIR /tmp

RUN ls

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw clean install

ENTRYPOINT ./mvnw spring-boot:run
