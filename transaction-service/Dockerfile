FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY build/libs/*SNAPSHOT.jar transaction.jar
ENTRYPOINT ["java", "-jar", "transaction.jar"]