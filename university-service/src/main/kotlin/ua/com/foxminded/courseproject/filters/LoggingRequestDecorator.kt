package ua.com.foxminded.courseproject.filters

import java.io.ByteArrayOutputStream
import java.nio.channels.Channels
import java.util.Optional
import org.slf4j.Logger
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.HttpMethod
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpRequestDecorator
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.scheduler.Schedulers

class LoggingRequestDecorator internal constructor(log: Logger, delegate: ServerHttpRequest) :
    ServerHttpRequestDecorator(delegate) {

    private val body: Flux<DataBuffer>

    init {
        val path = delegate.uri.path
        val query = delegate.uri.query
        val method = Optional.ofNullable(delegate.method).orElse(HttpMethod.GET).name
        log.info(
            "{} {}", method, path + (if (StringUtils.hasText(query)) "?$query" else "")
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

