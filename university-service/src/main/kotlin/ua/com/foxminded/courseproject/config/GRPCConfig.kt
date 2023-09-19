package ua.com.foxminded.courseproject.config

import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import protobuf.ReactorTransactionsServiceGrpc


@Configuration
class GRPCConfig {

    @Bean
    fun grpcStub(
        @Value("\${grpc.port}") port: Int,
        @Value("\${grpc.host}") host: String
    ): ReactorTransactionsServiceGrpc.ReactorTransactionsServiceStub {
        val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build()
        return ReactorTransactionsServiceGrpc.newReactorStub(channel)
    }
}
