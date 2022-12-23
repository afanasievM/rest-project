FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY target/*SNAPSHOT.jar transaction.jar
ENTRYPOINT ["java", "-jar", "transaction.jar"]