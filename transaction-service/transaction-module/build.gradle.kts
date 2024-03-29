
import org.springframework.boot.gradle.tasks.bundling.BootJar

val MAPSTRUCT_VER = "1.5.3.Final"
val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "3.1.4"
val GRPC_VER = "1.42.1"
val REACTIVE_GRPC_VER = "1.2.4"

plugins {
    java
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    id("io.spring.dependency-management")
    kotlin("jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
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
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-aop:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:$SPRINGBOOT_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$OPENAPI_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-core:$OPENAPI_VER")
    implementation("org.mapstruct:mapstruct:${MAPSTRUCT_VER}")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$SPRINGBOOT_VER")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    implementation("org.slf4j:slf4j-api:2.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.7")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("io.nats:jnats:2.16.8")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    implementation("io.grpc:grpc-all:$GRPC_VER")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    implementation("com.salesforce.servicelibs:grpc-spring:0.8.1")
    implementation("org.springframework.kafka:spring-kafka:3.0.11")
    implementation("io.projectreactor.kafka:reactor-kafka:1.3.21")
    implementation(project(":protobuf"))
    implementation(project(":transaction-service:rate-module"))

    kapt("org.mapstruct:mapstruct-processor:${MAPSTRUCT_VER}")

    implementation(kotlin("stdlib-jdk8"))
}

sourceSets {
    main {
        java.srcDirs.add(File("build/generated/source/apt/main"))
    }
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
tasks.getByName<Jar>("jar") {
    enabled = true
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
tasks.withType<JavaCompile>() {
    options.compilerArgs = listOf("-Amapstruct.suppressGeneratorTimestamp=true")
}

kotlin {
    jvmToolchain(17)
}
