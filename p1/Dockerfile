FROM maven:3.5.2-jdk-8-alpine


COPY pom.xml /tmp/pom.xml
COPY src /tmp/src/

WORKDIR /tmp
RUN mvn package

# FROM opdnjdk:8-jre-alpine

# WORKDIR /app

# COPY --from=MAVEN_BUILD /build/target/p1-0.0.1-SNAPSHOT.jar /app/

# ENTRYPOINT ["java", "-jar", "p1-0.0.1-SNAPSHOT.jar"]
