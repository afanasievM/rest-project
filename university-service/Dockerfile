FROM openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY target/*SNAPSHOT.jar university.jar
ENTRYPOINT ["java", "-jar", "university.jar"]