package ua.com.foxminded.courseproject.config

import org.bson.BsonArray
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import java.nio.file.Files

open class DBTestConfig {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    private val colections = arrayListOf(
        "classrooms", "days", "groups", "lessons", "schedule",
        "students", "subjects", "teachers", "transactions", "users", "weeks"
    )
    private val DATASET_PATH = "datasets/"

    @BeforeEach
    fun loadData() {
        colections.forEach {
            val jsonFile = ClassPathResource(DATASET_PATH + it + ".json").file
            val jsonString = String(Files.readAllBytes(jsonFile.toPath()))
            val bsonDocuments = BsonArray.parse(jsonString)
            println(bsonDocuments)
            mongoTemplate.insert(bsonDocuments, it)
        }
    }

    @AfterEach
    fun dropData() {
        colections.forEach {
            println(mongoTemplate.getCollection(it).find().toList())
            mongoTemplate.getCollection(it).drop()
        }
    }
}