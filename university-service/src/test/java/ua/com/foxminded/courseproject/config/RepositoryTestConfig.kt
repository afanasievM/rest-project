package ua.com.foxminded.courseproject.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean


@Configuration
class RepositoryTestConfig {
//    @Bean
//    fun objectMapper(): ObjectMapper {
//        val ptv: PolymorphicTypeValidator = BasicPolymorphicTypeValidator.builder()
//            .allowIfSubType("com.baeldung.jackson.inheritance")
//            .allowIfSubType("java.util.ArrayList")
//            .build()
//        val mapper = ObjectMapper()
//        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
//
//        return mapper
//    }
    @Bean
    fun getRepositoryPopulator(): Jackson2RepositoryPopulatorFactoryBean? {
        val factory = Jackson2RepositoryPopulatorFactoryBean()
//        factory.setMapper(objectMapper)
        factory.setResources(arrayOf(ClassPathResource("datasets/teachers.json")))
        return factory
    }

}