package ua.com.foxminded.courseproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.com.foxminded.courseproject.interceptors.RequestLoggingInterceptors;

import java.util.Collections;

@Configuration
@EnableWebMvc
public class WebConfig {

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }

    @Bean
    public RestTemplate restTemplate(RequestLoggingInterceptors requestLoggingInterceptors) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(requestLoggingInterceptors));
        return restTemplate;
    }
}
