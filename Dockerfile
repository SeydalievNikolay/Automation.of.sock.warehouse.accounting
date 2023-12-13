FROM openjdk:17
ARG JAR_FILE=target/automation.of.sock.warehouse.accounting-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} socks-app.jar
ENTRYPOINT ["java", "-jar", "/socks-app.jar"]