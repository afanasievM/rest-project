package ua.com.foxminded.courseproject.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean


@Configuration
class RepositoryTestConfig {
    @Bean
    fun getRepositoryPopulator(): Jackson2RepositoryPopulatorFactoryBean? {
        val factory = Jackson2RepositoryPopulatorFactoryBean()
        factory.setResources(arrayOf(ClassPathResource("datasets/teachers.json")))
        return factory
    }

}