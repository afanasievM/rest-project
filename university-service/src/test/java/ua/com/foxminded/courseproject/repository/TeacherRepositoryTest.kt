package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.MongoTransactionManager
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.findAll
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean
import org.springframework.data.util.Streamable
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.Transactional
import ua.com.foxminded.courseproject.config.RepositoryTestConfig
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*


@DataMongoTest
@Import(RepositoryTestConfig::class)
@AutoConfigureDataMongo
//@Transactional
@ExtendWith(SpringExtension::class)
@EnableTransactionManagement
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal open class TeacherRepositoryTest {

    @Autowired
    private lateinit var repository: TeacherRepository

    @Autowired
    lateinit var repositoryTestConfig: RepositoryTestConfig
    @Autowired
    lateinit var mongoTemplate: MongoTemplate



    @AfterEach
    fun cleanUpDatabase() {
        repositoryTestConfig.cleanupRepositoryPopulator()
        repository.findAll().forEach{ println(it)}
    }



    @Test
    fun findAll_shouldReturnListTeachers() {
        val expectedSize = 2

        val teachers = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, teachers.size)
    }

    @Test
    fun findById_shouldReturnTeacher_whenIdExists() {
        val id = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002")
        val expectedFirstname = "Yulia"

        repository.findAll().forEach{ println(it)}
        val actual = repository.findById(id).get()

        Assertions.assertEquals(expectedFirstname, actual.firstName)
    }

    @Test
    fun findById_shouldReturnEmptyOptional_whenIdNotExists() {
        val id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002")

        val actual = repository.findById(id)

        Assertions.assertEquals(true, actual.isEmpty)
    }

    @Test
    fun save_shouldAddTeacher_whenTeacherNotExists() {
        val testStr = "test"
        val expected = Teacher(firstName = testStr, id = UUID.randomUUID())
//        expected.firstName = testStr
        expected.lastName = testStr
        expected.degree = testStr
        expected.firstDay = LocalDate.now()
        expected.birthDay = LocalDate.now()
        expected.rank = testStr
        expected.title = testStr
        expected.salary = 1111

        repository.save(expected)

        Assertions.assertEquals(expected, expected.id?.let { repository.findById(it).get() })
    }

    @Test
    fun save_shouldChangeTeacher_whenTeacherExists() {
        val expected = Streamable.of(repository.findAll()).stream().findAny().get()
        expected.lastName = "test"

        repository.save(expected)

        Assertions.assertEquals(expected, expected.id?.let { repository.findById(it).get() })
    }

    @Test
    fun delete_shouldDeleteTeacher_whenTeacherExists() {
        val teacher = Streamable.of(repository.findAll()).stream().findAny().get()
        val expectedSize = 1

        repository.delete(teacher)
        val teachers = Streamable.of(repository.findAll()).stream().toList()

        Assertions.assertEquals(expectedSize, teachers.size)
    }

    @Test
    open fun delete_shouldNotDeleteTeachers_whenTeacherNotExists() {
        val testStr = "test"
        val teacher = Teacher(firstName = testStr, id = UUID.randomUUID())
        teacher.firstName = testStr
        teacher.lastName = testStr
        teacher.degree = testStr
        teacher.firstDay = LocalDate.now()
        teacher.birthDay = LocalDate.now()
        teacher.rank = testStr
        teacher.title = testStr
        teacher.salary = 1111
        val expectedSize = 2

        repository.delete(teacher)
        val teachers = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, teachers.size)
    }
}