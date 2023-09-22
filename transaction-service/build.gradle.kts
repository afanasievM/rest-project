
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val MAPSTRUCT_VER = "1.5.3.Final"
val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "2.7.4"
val GRPC_VER = "1.42.1"
val REACTIVE_GRPC_VER = "1.2.4"

plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.kotlin.kapt") version "1.8.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.21"
    id("io.gitlab.arturbosch.detekt") version "1.23.0"

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
    implementation("org.springframework.boot:spring-boot-starter-webflux:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-aop:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$SPRINGBOOT_VER")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive:$SPRINGBOOT_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:$OPENAPI_VER")
    implementation("org.springdoc:springdoc-openapi-webflux-core:$OPENAPI_VER")
    implementation("org.mapstruct:mapstruct:${MAPSTRUCT_VER}")
    implementation("org.apache.httpcomponents:httpclient:$HTTP_CLIENT_VER")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:$SPRINGBOOT_VER")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.21")
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("ch.qos.logback:logback-classic:1.2.9")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("io.nats:jnats:2.16.8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.7")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:$SPRINGBOOT_VER")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
    implementation("redis.clients:jedis")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    implementation("io.grpc:grpc-all:$GRPC_VER")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    implementation("com.salesforce.servicelibs:grpc-spring:0.8.1")
    implementation(project(":protobuf"))


    testImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    kapt("org.mapstruct:mapstruct-processor:${MAPSTRUCT_VER}")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
    testImplementation("org.testcontainers:testcontainers:1.18.3")
    testImplementation("io.nats:jnats:2.16.8")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")

}
sourceSets {
    main {
        java.srcDirs.add(File("build/generated/source/apt/main"))
    }
}

description = "restClient"

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
kapt {
    keepJavacAnnotationProcessors = false
    correctErrorTypes = true
    arguments {
        arg("mapstruct.nullValueCheckStrategy", "ALWAYS")
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}
