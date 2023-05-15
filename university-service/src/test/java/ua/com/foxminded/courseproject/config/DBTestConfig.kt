package ua.com.foxminded.courseproject.config

import com.mongodb.DBObject
import org.bson.BsonArray
import org.bson.BsonDocument
import org.bson.Document
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.core.io.ClassPathResource
import org.springframework.data.mongodb.core.MongoTemplate
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import java.nio.file.Files

@Testcontainers
@DataMongoTest
open class DBTestConfig {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate



    private val colections = arrayListOf(
        "classrooms", "days", "groups", "lessons", "schedule",
        "students", "subjects", "teachers", "transactions", "users", "weeks"
    )
    private val DATASET_PATH = "datasets/"

    companion object {
        @Container
        private val mongoDBContainer = GenericContainer(DockerImageName.parse("mongo:6.0.5"))
            .withExposedPorts(27018)



        @BeforeAll
        @JvmStatic
        fun setUp() {
//            mongoDBContainer.firstMappedPort(27020)
            mongoDBContainer.start()
            System.setProperty("spring.data.mongodb.host", mongoDBContainer.containerIpAddress)
            System.setProperty("spring.data.mongodb.port", mongoDBContainer.getMappedPort(27018).toString())
            println("Mapped Port: ${mongoDBContainer.getMappedPort(27018)}")

        }

        @AfterAll
        @JvmStatic
        fun tearDown(){
            mongoDBContainer.stop()
        }
    }
    @BeforeEach
    fun loadData() {

        colections.forEach {
            val jsonFile = ClassPathResource(DATASET_PATH + it + ".json").file
            val jsonString = String(Files.readAllBytes(jsonFile.toPath()))
            val jsonArray = JSONArray(jsonString)
//            val jsonObj = JSONObject(jsonArray)
            for(i in 0 until jsonArray.length()){
                println(jsonArray.getJSONObject(i).toString())
                val document = BsonDocument.parse(jsonArray.getJSONObject(i).toString())
                println(document.toString())
                mongoTemplate.insert(document,it)
            }
//            mongoTemplate.insert(jsonArray, it)
        }
    }

    @AfterEach
    fun dropData() {
        colections.forEach {
            mongoTemplate.getCollection(it).drop()
        }
    }

}