package ua.com.foxminded.restClient.filters

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ControllersFilter : Filter {
    private val log = LoggerFactory.getLogger(ControllersFilter::class.java)

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        super.init(filterConfig)
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val wreq = ContentCachingRequestWrapper(request as HttpServletRequest)
        val wres = ContentCachingResponseWrapper(response as HttpServletResponse)
        log.info(
            "{}: {}?{}",
            wreq.method,
            wreq.requestURI,
            if (wreq.queryString != null) wreq.queryString else ""
        )
        chain.doFilter(wreq, wres)
        while (wreq.inputStream.read() >= 0) {
        }
        log.info("Response: {}", String(wres.contentAsByteArray))
        wres.copyBodyToResponse()
    }

    override fun destroy() {
        super.destroy()
    }
}
