
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "2.7.4"
val GRPC_VER = "1.53.0"
val REACTIVE_GRPC_VER = "1.2.4"

plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jetbrains.kotlin.plugin.spring")
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
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
    implementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    implementation("io.grpc:protoc-gen-grpc-kotlin:1.3.1")
    implementation("io.grpc:grpc-all:$GRPC_VER")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    implementation("com.salesforce.servicelibs:grpc-spring:0.8.1")
    implementation(project(":protobuf"))


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

sourceSets {
    main {
        java.srcDirs.add(File("build/generated/source/apt/main"))
    }
}

tasks.test {
    useJUnitPlatform()
}

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
