FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN ./gradlew build -x test

FROM --platform=linux/amd64 openjdk:17-jdk-alpine
ARG JAR_FILE=*.jar
COPY --from=gradleimage /home/gradle/source/build/libs/*SNAPSHOT.jar transaction.jar
ENTRYPOINT ["java", "-jar", "transaction.jar"]