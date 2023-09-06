import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "2.7.4"

plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21"
    kotlin("jvm") version "1.8.21"
    id("io.gitlab.arturbosch.detekt") version "1.23.0"
    `java-test-fixtures`
}

repositories {
    mavenCentral()
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-aop:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-validation:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$SPRINGBOOT_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$OPENAPI_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-core:$OPENAPI_VER")
    implementation("org.springframework.boot:spring-boot-starter-security:$SPRINGBOOT_VER")
    implementation("org.apache.httpcomponents:httpclient:$HTTP_CLIENT_VER")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")



    testImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.1.0")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))


    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("org.testcontainers:mongodb:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")
    testImplementation("io.projectreactor:reactor-test")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-validation:$SPRINGBOOT_VER")


}

tasks.test {
    useJUnitPlatform()
}

group = "ua.com.foxminded"
version = "0.0.1-SNAPSHOT"
description = "university-service"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
kotlin {
    jvmToolchain(17)
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
}

tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}
