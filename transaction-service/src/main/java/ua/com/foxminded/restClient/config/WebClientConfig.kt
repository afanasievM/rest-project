package ua.com.foxminded.restClient.config

import jdk.incubator.vector.VectorOperators.LOG
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.*
import reactor.core.publisher.Mono
import java.io.IOException
import java.util.*


@Component
class WebClientConfig {

    private val log = LoggerFactory.getLogger(WebClientConfig::class.java)

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .filter(logRequest())
            .filter(logResponse())
            .build()
    }

    private fun logRequest(): ExchangeFilterFunction {
        return ExchangeFilterFunction { clientRequest: ClientRequest, next: ExchangeFunction ->
            log.info("===========================request begin================================================")
            log.info("URI         : {}", clientRequest.url())
            log.info("Method      : {}", clientRequest.method())
            log.info("Headers     : {}", clientRequest.headers())
            log.info("Request body: {}", clientRequest.body().toString())
            log.info("==========================request end================================================")
            next.exchange(clientRequest)
        }
    }

    private fun logResponse(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofResponseProcessor { clientResponse: ClientResponse ->
            log.info("============================response begin==========================================")
            log.info("Status code  : {}", clientResponse.statusCode())
            log.info("Headers      : {}", clientResponse.headers().asHttpHeaders().toString())
//            clientResponse.body()
            clientResponse
                .bodyToMono(String::class.java)
                .flatMap ( body -> {
                        log.debug("Body is {}", body)
                        return Mono.just(clientResponse)
                    }
            )


            log.info("=======================response end=================================================")
            Mono.just(clientResponse)
        }
    }

    @Throws(IOException::class)
    private fun logResponse(response: ClientHttpResponse) {


    }
}