import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import com.google.protobuf.gradle.*

val MAPSTRUCT_VER = "1.5.3.Final"
val OPENAPI_VER = "1.7.0"
val HTTP_CLIENT_VER = "4.5.13"
val SPRINGBOOT_VER = "2.7.4"

plugins {
    java
    id("org.springframework.boot") version "2.7.11"
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.21"
    id("org.jetbrains.kotlin.kapt") version "1.8.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.21"
    id("io.gitlab.arturbosch.detekt") version "1.23.0"
    id("com.google.protobuf") version "0.9.4"

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
    implementation("com.google.protobuf:protobuf-kotlin:3.19.6")


    testImplementation("org.springframework.boot:spring-boot-starter-test:$SPRINGBOOT_VER")
    kapt("org.mapstruct:mapstruct-processor:${MAPSTRUCT_VER}")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))


}
sourceSets {
    main {
        java.srcDirs.add(File("build/generated/source/apt/main"))
        proto {
            srcDir("src/main/protobuf")
        }
    }
}

group = "ua.com.foxminded"
version = "0.0.1-SNAPSHOT"
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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.19.6"
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                id("kotlin")
            }
        }
    }
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = "17"
}
tasks.withType<DetektCreateBaselineTask>().configureEach {
    jvmTarget = "17"
}
