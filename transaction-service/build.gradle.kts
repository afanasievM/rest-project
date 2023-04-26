import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

/*
 * This file was generated by the Gradle 'init' task.
 */
val MAPSTRUCT_VER = "1.5.3.Final"
val LOMBOK_VER = "1.18.26"
val OPENAPI_VER = "1.6.14"
val HTTP_CLIENT_VER = "4.5.13"
val POSTGRES_VER = "42.5.1"
val FLYWAY_CORE_VER = "8.5.13"
val SPRINGBOOT_VER = "2.7.4"

plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.21"
    kotlin("kapt") version "1.8.21"
}

kapt {
    keepJavacAnnotationProcessors = false
    correctErrorTypes = true
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-web:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$SPRINGBOOT_VER")
    implementation("org.flywaydb:flyway-core:$FLYWAY_CORE_VER")
    implementation("org.springdoc:springdoc-openapi-ui:$OPENAPI_VER")
    implementation("org.mapstruct:mapstruct:$MAPSTRUCT_VER")
    implementation("org.apache.httpcomponents:httpclient:$HTTP_CLIENT_VER")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$SPRINGBOOT_VER")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    runtimeOnly("org.postgresql:postgresql:$POSTGRES_VER")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    compileOnly("org.projectlombok:lombok:$LOMBOK_VER")
    annotationProcessor("org.projectlombok:lombok:$LOMBOK_VER")
//    annotationProcessor ("org.mapstruct:mapstruct-processor:$MAPSTRUCT_VER")
    kapt("org.projectlombok:lombok:$LOMBOK_VER")
    annotationProcessor("org.mapstruct:mapstruct-processor:$MAPSTRUCT_VER")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}
sourceSets {
    main{
        java.srcDirs.add(File("build/generated/source/apt/main"))
    }
}

group = "ua.com.foxminded"
version = "0.0.1-SNAPSHOT"
description = "restClient"


tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
tasks.withType<JavaCompile>() {
    options.compilerArgs = listOf("-Amapstruct.suppressGeneratorTimestamp=true")
}

kotlin {
    jvmToolchain(17)
}
