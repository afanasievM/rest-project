import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.proto

val GRPC_VER = "1.53.0"
val REACTIVE_GRPC_VER = "1.2.4"

plugins {
    java
    kotlin("jvm") version "1.8.21"
    id("com.google.protobuf") version "0.9.4"
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://oss.sonatype.org/content/repositories/")
    }
}

dependencies {
    implementation("io.projectreactor:reactor-core:3.4.29")
    implementation("com.google.protobuf:protobuf-kotlin:3.24.3")
    implementation("io.grpc:protoc-gen-grpc-kotlin:1.3.1")
    implementation("io.grpc:grpc-all:$GRPC_VER")
    implementation("com.salesforce.servicelibs:reactor-grpc-stub:$REACTIVE_GRPC_VER")
    implementation("com.salesforce.servicelibs:grpc-spring:0.8.1")
}

sourceSets {
    main {
        proto {
            srcDir("/proto")
        }
    }
}

description = "protobuf"
java.sourceCompatibility = JavaVersion.VERSION_17


protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${GRPC_VER}"
        }
        id("reactor-grpc") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:${REACTIVE_GRPC_VER}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("reactor-grpc")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
}
