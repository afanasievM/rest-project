package ua.com.foxminded.restClient.interceptors

import org.slf4j.LoggerFactory
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.stereotype.Component
import org.springframework.util.StreamUtils
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

@Component
class RequestLoggingInterceptors : ClientHttpRequestInterceptor {
    private val log = LoggerFactory.getLogger(RequestLoggingInterceptors::class.java)

    @Throws(IOException::class)
    override fun intercept(
        request: HttpRequest,
        body: ByteArray,
        execution: ClientHttpRequestExecution
    ): ClientHttpResponse {
        logRequest(request, body)
        val response = execution.execute(request, body)
        logResponse(response)
        return response
    }

    @Throws(IOException::class)
    private fun logRequest(request: HttpRequest, body: ByteArray) {
        log.info("===========================request begin================================================")
        log.info("URI         : {}", request.uri)
        log.info("Method      : {}", request.method)
        log.info("Headers     : {}", request.headers)
        log.info("Request body: {}", String(body, StandardCharsets.UTF_8))
        log.info("==========================request end================================================")
    }

    @Throws(IOException::class)
    private fun logResponse(response: ClientHttpResponse) {
        log.info("============================response begin==========================================")
        log.info("Status code  : {}", response.statusCode)
        log.info("Status text  : {}", response.statusText)
        log.info("Headers      : {}", response.headers)
        log.info("Response body: {}", StreamUtils.copyToString(response.body, Charset.defaultCharset()))
        log.info("=======================response end=================================================")
    }
}
