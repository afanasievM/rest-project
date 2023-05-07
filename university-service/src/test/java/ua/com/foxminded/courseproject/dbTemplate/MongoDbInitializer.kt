//package ua.com.foxminded.courseproject.dbTemplate
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.KotlinModule
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import java.io.File
//
//@SpringBootTest
//@ExtendWith(SpringExtension::class)
//class MongoDbInitializer {
//    @Autowired
//    lateinit var mongoTemplate: MongoTemplate
//
//    @BeforeEach
//    fun setUp() {
//        val objectMapper = ObjectMapper().registerModule(KotlinModule())
//        val dataSet = FileDataSet(File("src/test/resources/data.json"), objectMapper)
//        dataSet.load(mongoTemplate)
//    }
//}