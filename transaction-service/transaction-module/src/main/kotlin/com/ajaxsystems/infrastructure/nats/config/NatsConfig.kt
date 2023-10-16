package com.ajaxsystems.infrastructure.nats.config

import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class NatsConfig {
    @Bean
    fun natsConncetion(@Value("\${nats.url}") natsUrl: String): Connection {
        return Nats.connect(natsUrl)
    }
}
