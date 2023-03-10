package ua.com.foxminded.courseproject.filters;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ControllersFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper wreq = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper wres = new ContentCachingResponseWrapper((HttpServletResponse) response);
        logger.info("{}: {}?{}",wreq.getMethod() ,wreq.getRequestURI(), wreq.getQueryString() != null ? wreq.getQueryString() : "");
        chain.doFilter(wreq, wres);
        while (wreq.getInputStream().read() >= 0) {
        }
        logger.info("Response: {}", new String(wres.getContentAsByteArray()));
        wres.copyBodyToResponse();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
