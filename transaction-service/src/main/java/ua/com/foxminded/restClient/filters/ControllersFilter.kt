package ua.com.foxminded.restClient.filters

import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.ServerWebExchangeDecorator
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono
import reactor.core.scheduler.Schedulers
import java.io.ByteArrayOutputStream
import java.nio.channels.Channels
import java.util.*


@Component
class ControllersFilter : WebFilter {
    private val log = LoggerFactory.getLogger(ControllersFilter::class.java)

    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ) = chain.filter(LoggingWebExchange(log, exchange))

}

class LoggingWebExchange(log: Logger, delegate: ServerWebExchange) : ServerWebExchangeDecorator(delegate) {
    private val requestDecorator: LoggingRequestDecorator = LoggingRequestDecorator(log, delegate.request)
    private val responseDecorator: LoggingResponseDecorator = LoggingResponseDecorator(log, delegate.response)
    override fun getRequest(): ServerHttpRequest {
        return requestDecorator
    }

    override fun getResponse(): ServerHttpResponse {
        println("resp")
        println(responseDecorator)
        return responseDecorator
    }
}

class LoggingResponseDecorator internal constructor(val log: Logger, delegate: ServerHttpResponse) :
    ServerHttpResponseDecorator(delegate) {

    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
        println("wrint")
        return super.writeWith(
            Flux.from(body)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext { buffer: DataBuffer ->
                    val bodyStream = ByteArrayOutputStream()
                    Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                    log.info(
                        "{}: {} - {} : {}",
                        "response",
                        String(bodyStream.toByteArray()),
                        "header",
                        delegate.headers
                    )
                })
    }

    override fun writeAndFlushWith(body: Publisher<out Publisher<out DataBuffer>>): Mono<Void> {
        return super.writeAndFlushWith(
            Flux.from(body).doOnNext {
                it.toMono()
            }

//                .doOnNext {it.toFlux().log()
//                Flux.from(it).doOnNext { buffer: DataBuffer ->
//                        val bodyStream = ByteArrayOutputStream()
//                        Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
//                        log.info(
//                            "{}: {} - {} : {}",
//                            "response",
//                            String(bodyStream.toByteArray()),
//                            "header",
//                            delegate.headers
//                        )
//                    }
//                }
//                .log()
        )
//                .doOnNext { buffer: DataBuffer ->
////                if (log.isDebugEnabled) {
//                    val bodyStream = ByteArrayOutputStream()
//                    Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
//                    log.info(
//                        "{}: {} - {} : {}",
//                        "response",
//                        String(bodyStream.toByteArray()),
//                        "header",
//                        delegate.headers
//                    )
////                }
//                })

    }
}

class LoggingRequestDecorator internal constructor(log: Logger, delegate: ServerHttpRequest) :
    ServerHttpRequestDecorator(delegate) {

    private val body: Flux<DataBuffer>?

//    override fun getBody(): Flux<DataBuffer> {
//        println("body")
//        return body!!
//    }

    init {
        val path = delegate.uri.path
        val query = delegate.uri.query
        val method = Optional.ofNullable(delegate.method).orElse(HttpMethod.GET).name
        val headers = delegate.headers
        log.info(
            "{} {}\n {}", method, path + (if (StringUtils.hasText(query)) "?$query" else ""), headers
        )
        body = super.getBody()
            .publishOn(Schedulers.boundedElastic())
            .doOnNext { buffer: DataBuffer ->
                val bodyStream = ByteArrayOutputStream()
                Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                println(buffer.asByteBuffer().asReadOnlyBuffer().get())
                log.info("{}: {}", "request", String(bodyStream.toByteArray()))
            }
    }
}
