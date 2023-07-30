package ua.com.foxminded.courseproject.config

import com.mongodb.reactivestreams.client.MongoClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.config.EnableWebFlux
import ua.com.foxminded.courseproject.interceptors.RequestLoggingInterceptors
import ua.com.foxminded.courseproject.utils.NbDbRefResolver


@Configuration
@EnableWebFlux
class WebConfig {

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


    @Bean
    fun mappingMongoConverter(
        mongoMappingContext: MongoMappingContext,
        mongoClient: MongoClient
    ): MappingMongoConverter? {
        val converter =
            MappingMongoConverter(
                nbDbRefResolver(),
                mongoMappingContext
            )
        converter.typeMapper = DefaultMongoTypeMapper(null)
        return converter
    }

    @Bean
    fun nbDbRefResolver():NbDbRefResolver{
        return NbDbRefResolver()
    }


//    @Bean
//    fun reactiveMongoDatabaseFactory(): SimpleReactiveMongoDatabaseFactory {
//        return SimpleReactiveMongoDatabaseFactory(mongoClient)
//    }
//
//    @Bean
//    fun reactiveMongoTemplate(): ReactiveMongoTemplate {
//        return ReactiveMongoTemplate(reactiveMongoDatabaseFactory())
//    }
}
