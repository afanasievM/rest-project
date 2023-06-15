package ua.com.foxminded.restClient.config

import io.nats.client.Connection
import io.nats.client.Dispatcher
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import ua.com.foxminded.restClient.interceptors.RequestLoggingInterceptors

@Configuration
class WebConfig {
    @Value("\${nats.url}")
    private lateinit var natsUrl: String
    @Bean
    fun restTemplate(requestLoggingInterceptors: RequestLoggingInterceptors): RestTemplate {
        val factory: ClientHttpRequestFactory =
            BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory())
        val restTemplate = RestTemplate(factory)
        restTemplate.interceptors = listOf<ClientHttpRequestInterceptor>(requestLoggingInterceptors)
        return restTemplate
    }

    @Bean
    fun natsConncetion(): Connection {
        return Nats.connect(natsUrl)
    }
}
