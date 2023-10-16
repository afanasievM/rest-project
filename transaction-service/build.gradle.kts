
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.springframework.boot.gradle.tasks.bundling.BootJar

val MAPSTRUCT_VER = "1.5.3.Final"
val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "3.1.4"
val GRPC_VER = "1.42.1"
val REACTIVE_GRPC_VER = "1.2.4"

subprojects {

    group = "com.ajaxsystems"
    version = "0.0.1-SNAPSHOT"

    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java-library")

    repositories {
        mavenCentral()

        maven {
            url = uri("https://oss.sonatype.org/content/repositories/")
        }
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:$SPRINGBOOT_VER")
        }
    }

}

plugins {
    java
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
    id("io.spring.dependency-management")
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
    `java-test-fixtures`
}

repositories {
    mavenCentral()

    maven {
        url = uri("https://oss.sonatype.org/content/repositories/")
    }
}

detekt {
    buildUponDefaultConfig = true // preconfigure defaults
    allRules = false // activate all available (even unstable) rules.
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$SPRINGBOOT_VER")
    implementation(project(":transaction-service:transaction-module"))

    testImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    testImplementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:$SPRINGBOOT_VER")
    testImplementation(kotlin("test"))
    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("org.testcontainers:mongodb:1.18.3")
    testImplementation("io.nats:jnats:2.16.8")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.kafka:spring-kafka-test:3.0.11")
    testImplementation("org.springframework.kafka:spring-kafka:3.0.11")
    testImplementation("io.projectreactor.kafka:reactor-kafka:1.3.21")
    testImplementation(project(":transaction-service:transaction-module"))
    testImplementation(project(":transaction-service:rate-module"))
    testImplementation(project(":protobuf"))
    testImplementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    testImplementation("io.grpc:grpc-all:$GRPC_VER")
    testImplementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    testImplementation("com.salesforce.servicelibs:grpc-spring:0.8.1")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    testFixturesImplementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    testFixturesImplementation("io.grpc:grpc-all:$GRPC_VER")
    testFixturesImplementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    testFixturesImplementation("com.salesforce.servicelibs:grpc-spring:0.8.1")
    testFixturesImplementation(project(":protobuf"))
    testFixturesImplementation(project(":transaction-service:transaction-module"))
    testFixturesImplementation(project(":transaction-service:rate-module"))
    testFixturesImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    testFixturesImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.5")
}

springBoot {
    mainClass = "com.ajaxsystems.RestClientApplicationKt"
}

sourceSets {
    main {
        java.srcDirs.add(File("build/generated/source/apt/main"))
    }
}

description = "transaction-service"

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

tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}
