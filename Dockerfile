FROM openjdk:17
ARG JAR_FILE=com.example.skypro.automation.of.sock.warehouse.accounting/target/automation.of.sock.warehouse.accounting-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} automation.of.sock.warehouse.accounting-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "automation.of.sock.warehouse.accounting-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080