package ua.com.foxminded.restClient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ua.com.foxminded.restClient.interceptors.RequestLoggingInterceptors;

import java.util.Collections;


@Configuration
public class WebConfig {

    @Bean
    public RestTemplate restTemplate(RequestLoggingInterceptors requestLoggingInterceptors) {
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory());
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(requestLoggingInterceptors));
        return restTemplate;
    }


}
