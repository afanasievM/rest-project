group = "ua.com.foxminded"
version = "0.0.1-SNAPSHOT"

plugins {
    id("org.springframework.boot") version "2.7.11" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    id("org.jetbrains.kotlin.plugin.spring") version "1.8.21" apply false
    kotlin("jvm") version "1.8.21" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.21" apply false
    id("org.jetbrains.kotlin.plugin.allopen") version "1.8.21" apply false
}
