package ua.com.foxminded.courseproject.config

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfig
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.runtime.Network
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement


@Configuration
@EnableTransactionManagement
@AutoConfigureDataMongo
class RepositoryTestConfig {

    private lateinit var repositoryPopulator: Jackson2RepositoryPopulatorFactoryBean


//    @BeforeAll
//    fun setup() {
//        val starter = MongodStarter.getDefaultInstance()
//        mongodExecutable = starter.prepare(mongoConfig)
//        mongodProcess = mongodExecutable.start()
//    }

    @AfterEach
    fun cleanup() {
        cleanupRepositoryPopulator()
    }

//    @Bean
//    fun getRepositoryPopulator(): Jackson2RepositoryPopulatorFactoryBean? {
//        val factory = Jackson2RepositoryPopulatorFactoryBean()
//        factory.setResources(arrayOf(ClassPathResource("datasets/teachers.json")))
//        return factory
//    }
    @Bean
    fun getRepositoryPopulator(): Jackson2RepositoryPopulatorFactoryBean {
        if (!::repositoryPopulator.isInitialized) {
            repositoryPopulator = Jackson2RepositoryPopulatorFactoryBean()
            repositoryPopulator.setResources(arrayOf(ClassPathResource("datasets/teachers.json")))
            repositoryPopulator.afterPropertiesSet()
        }
        return repositoryPopulator
    }

    @Bean
    @DependsOn("getRepositoryPopulator")
    fun cleanupRepositoryPopulator() {
        repositoryPopulator.destroy()
        repositoryPopulator = Jackson2RepositoryPopulatorFactoryBean()
        repositoryPopulator.setResources(arrayOf(ClassPathResource("datasets/teachers.json")))
        repositoryPopulator.afterPropertiesSet()
    }

}