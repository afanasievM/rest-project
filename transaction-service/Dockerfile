FROM gradle:jdk17 as gradleimage
COPY . /home/gradle/source
WORKDIR /home/gradle/source
RUN ./gradlew clean
RUN ./gradlew :transaction-service:build -x test

FROM --platform=linux/amd64 openjdk:17-alpine
ARG JAR_FILE=*.jar
COPY --from=gradleimage /home/gradle/source/transaction-service/build/libs/transaction-service.jar transaction.jar
ENTRYPOINT ["java", "-jar", "transaction.jar"]
