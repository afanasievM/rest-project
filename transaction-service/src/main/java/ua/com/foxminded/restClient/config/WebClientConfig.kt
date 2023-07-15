package ua.com.foxminded.restClient.config

import io.netty.handler.logging.LogLevel
import org.springframework.context.annotation.Bean
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat


@Component
class WebClientConfig {
    @Bean
    fun httpClient(): HttpClient {
        return HttpClient
            .create()
            .wiretap(
                "reactor.netty.http.client.HttpClient",
                LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL
            )
    }

    @Bean
    fun webClient(httpClient: HttpClient): WebClient {
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

}

