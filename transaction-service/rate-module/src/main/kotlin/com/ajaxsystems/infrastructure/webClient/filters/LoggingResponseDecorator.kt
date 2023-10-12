package com.ajaxsystems.infrastructure.webClient.filters

import java.io.ByteArrayOutputStream
import java.nio.channels.Channels
import org.reactivestreams.Publisher
import org.slf4j.Logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.http.server.reactive.ServerHttpResponseDecorator
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers

class LoggingResponseDecorator internal constructor(val log: Logger, delegate: ServerHttpResponse) :
    ServerHttpResponseDecorator(delegate) {

    @Suppress("OnlyForLogs")
    override fun writeWith(body: Publisher<out DataBuffer>): Mono<Void> {
        return super.writeWith(
            Flux.from(body)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext { buffer: DataBuffer ->
                    val bodyStream = ByteArrayOutputStream()
                    Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                    log.info(
                        "{}: {}",
                        "response",
                        String(bodyStream.toByteArray())
                    )
                })
    }

    @Suppress("OnlyForLogs")
    override fun writeAndFlushWith(body: Publisher<out Publisher<out DataBuffer>>): Mono<Void> {
        return super.writeAndFlushWith(
            Flux.merge(body)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext { buffer: DataBuffer ->
                    val bodyStream = ByteArrayOutputStream()
                    Channels.newChannel(bodyStream).write(buffer.asByteBuffer().asReadOnlyBuffer())
                    log.info(
                        "{}: {}",
                        "response",
                        String(bodyStream.toByteArray()).strip(),
                    )
                }.window(1)
        )

    }

}
