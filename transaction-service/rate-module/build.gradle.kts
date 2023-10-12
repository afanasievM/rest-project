import org.springframework.boot.gradle.tasks.bundling.BootJar

val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "3.1.4"
val REACTIVE_GRPC_VER = "1.2.4"

plugins {
    java
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    kotlin("jvm")
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-webflux:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-aop:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$SPRINGBOOT_VER")
    implementation("org.apache.httpcomponents:httpclient:$HTTP_CLIENT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$SPRINGBOOT_VER")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("redis.clients:jedis:4.4.5")
    implementation("org.slf4j:slf4j-api:2.0.5")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
tasks.getByName<Jar>("jar") {
    enabled = false
}
