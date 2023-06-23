package ua.com.foxminded.restClient.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.nats.client.Connection
import io.nats.client.Nats
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import ua.com.foxminded.restClient.interceptors.RequestLoggingInterceptors
import java.time.Duration

@Configuration
class WebConfig {
    @Value("\${nats.url}")
    private lateinit var natsUrl: String

    @Bean
    fun restTemplate(requestLoggingInterceptors: RequestLoggingInterceptors): RestTemplate {
        val factory: ClientHttpRequestFactory =
            BufferingClientHttpRequestFactory(HttpComponentsClientHttpRequestFactory())
        val restTemplate = RestTemplate(factory)
        restTemplate.interceptors = listOf<ClientHttpRequestInterceptor>(requestLoggingInterceptors)
        return restTemplate
    }

    @Bean
    fun natsConncetion(): Connection {
        return Nats.connect(natsUrl)
    }

    @Bean
    fun mapper(): ObjectMapper {
        return jacksonObjectMapper().apply {
            registerModule(JavaTimeModule())
        }
    }

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer {
        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManager.RedisCacheManagerBuilder ->
            builder
                .withCacheConfiguration(
                    "currency",
                    RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(1))
                        .disableCachingNullValues()
                        .serializeValuesWith(
                            RedisSerializationContext.SerializationPair.fromSerializer(
                                GenericJackson2JsonRedisSerializer(
                                    mapper()
                                        .copy()
                                        .enableDefaultTyping(
                                            ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY
                                        )
                                )
                            )
                        )
                )
        }
    }
}
