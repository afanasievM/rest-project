/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */


val OPENAPI_VER = "1.6.13"
val HTTP_CLIENT_VER = "4.5.13"
val POSTGRES_VER = "42.5.1"



plugins {
    id("org.springframework.boot") version "2.7.11"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21"
    kotlin("jvm") version "1.8.21"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-validation:")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springdoc:springdoc-openapi-ui:$OPENAPI_VER")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.apache.httpcomponents:httpclient:$HTTP_CLIENT_VER")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))


    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testImplementation("io.projectreactor:reactor-test")


}

tasks.test {
    useJUnitPlatform()
}

group = "ua.com.foxminded"
version = "0.0.1-SNAPSHOT"
description = "courseproject"
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