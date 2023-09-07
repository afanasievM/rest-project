package ua.com.foxminded.restClient.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext


@Configuration
class RedisConfig {
    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory, mapper: ObjectMapper): CacheManager {
        val jackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer(
            mapper.copy()
                .enableDefaultTyping(
                    ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY
                )
        )
        val redisSerializationContext = RedisSerializationContext
            .SerializationPair
            .fromSerializer(jackson2JsonRedisSerializer)
        return RedisCacheManager.builder(redisConnectionFactory)
            .cacheDefaults(
                RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(1))
                    .disableCachingNullValues()
                    .serializeValuesWith(redisSerializationContext)
            )
            .build()
    }
}
