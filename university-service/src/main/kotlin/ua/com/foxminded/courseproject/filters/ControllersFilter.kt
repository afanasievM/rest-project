package ua.com.foxminded.courseproject.filters

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain

@Component
class ControllersFilter : WebFilter {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    override fun filter(
        exchange: ServerWebExchange,
        chain: WebFilterChain
    ) = chain.filter(LoggingWebExchange(logger, exchange))
}
