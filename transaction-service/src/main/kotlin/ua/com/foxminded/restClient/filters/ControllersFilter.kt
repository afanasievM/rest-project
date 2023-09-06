package ua.com.foxminded.restClient.filters

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain


@Component
class ControllersFilter : WebFilter {
    private val log = LoggerFactory.getLogger(ControllersFilter::class.java)

    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ) = chain.filter(LoggingWebExchange(log, exchange))

}



