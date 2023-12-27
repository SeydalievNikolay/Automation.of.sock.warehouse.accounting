FROM maven:3.9.6-eclipse-temurin-17 as build
COPY src src
COPY pom.xml pom.xml
RUN mvn clean package dependency:copy-dependencies -DincludeScope=runtime

FROM openjdk:17
COPY --from=build target/dependency ./lib
COPY --from=build target/automation.of.sock.warehouse.accounting-0.0.1-SNAPSHOT.jar ./socks-app.jar
ENTRYPOINT ["java", "-cp", "./lib/*:./socks-app.jar"]