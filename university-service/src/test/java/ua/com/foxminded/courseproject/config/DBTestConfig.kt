package ua.com.foxminded.courseproject.config

import org.bson.BsonDocument
import org.json.JSONArray
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.junit.jupiter.Testcontainers
import java.nio.file.Files

@Testcontainers
@DataMongoTest
//@TestPropertySource(locations = ["classpath:application.properties"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class DBTestConfig {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate
    private val DATASET_PATH = "datasets/"
    private lateinit var mongoContainer: DockerComposeContainer<*>


    private val colections = arrayListOf(
        "classrooms", "days", "groups", "lessons", "schedule",
        "students", "subjects", "teachers", "transactions", "users", "weeks"
    )

    @BeforeAll
    fun setUp() {
        mongoContainer = DockerComposeContainer(ClassPathResource("docker-compose-test.yaml").file)
        mongoContainer.start()
    }


    @AfterAll
    fun tearDown() {
        mongoContainer.stop()

    }

    @BeforeEach
    fun loadData() {
        colections.forEach {
            val jsonFile = ClassPathResource(DATASET_PATH + it + ".json").file
            val jsonString = String(Files.readAllBytes(jsonFile.toPath()))
            val jsonArray = JSONArray(jsonString)
            for (i in 0 until jsonArray.length()) {
                val document = BsonDocument.parse(jsonArray.getJSONObject(i).toString())
                mongoTemplate.insert(document, it)
            }
        }
    }

    @AfterEach
    fun dropData() {
        colections.forEach {
            mongoTemplate.getCollection(it).drop()
        }
    }

}