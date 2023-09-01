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
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer
import java.time.Duration


@Configuration
@EnableWebFlux
class WebConfig: WebFluxConfigurer {
    @Value("\${nats.url}")
    private lateinit var natsUrl: String

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

    override fun configureArgumentResolvers(configurer: ArgumentResolverConfigurer) {
        configurer.addCustomResolver(ReactivePageableHandlerMethodArgumentResolver())
    }
}
