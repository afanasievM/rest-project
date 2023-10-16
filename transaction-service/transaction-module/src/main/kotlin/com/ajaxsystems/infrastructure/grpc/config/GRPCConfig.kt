package com.ajaxsystems.infrastructure.grpc.config

import com.salesforce.grpc.contrib.spring.GrpcServerHost
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GRPCConfig {

    @Bean(initMethod = "start")
    fun grpcServer(@Value("\${grpc.port}") port: Int): GrpcServerHost {
        return GrpcServerHost(port)
    }
}
