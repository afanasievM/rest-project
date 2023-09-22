package ua.com.foxminded.courseproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import ua.com.foxminded.courseproject.interceptors.RequestLoggingInterceptors


@Configuration
@EnableWebFlux
class WebConfig : WebFluxConfigurer {

    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults {
        return GrantedAuthorityDefaults("")
    }

    @Bean
    fun restTemplate(requestLoggingInterceptors: RequestLoggingInterceptors): RestTemplate {
        val factory: ClientHttpRequestFactory =
            BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory())
        val restTemplate = RestTemplate(factory)
        restTemplate.interceptors = listOf<ClientHttpRequestInterceptor>(requestLoggingInterceptors)
        return restTemplate
    }

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(ReactivePageableHandlerMethodArgumentResolver())
    }

}
