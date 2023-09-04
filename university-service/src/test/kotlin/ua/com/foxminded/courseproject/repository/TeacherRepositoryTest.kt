package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*


@DataMongoTest
internal open class TeacherRepositoryTest : DBTestConfig() {

    @Autowired
    private lateinit var repository: TeacherRepository

    @Test
    fun findAll_shouldReturnFluxTeachers() {
        val expectedSize = 2

        val teachers = repository.findAll()

        StepVerifier.create(teachers)
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()


    }

    @Test
    fun findById_shouldReturnTeacher_whenIdExists() {
        val id = UUID.fromString("e966f608-4621-11ed-b878-0242ac120002")
        val expectedFirstname = "Yulia"

        val actual = repository.findById(id)

        StepVerifier.create(actual)
            .expectNextMatches { expectedFirstname == it.firstName }
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnEmptyMono_whenIdNotExists() {
        val id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002")

        val actual = repository.findById(id)

        StepVerifier.create(actual)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun save_shouldAddTeacher_whenTeacherNotExists() {
        val testStr = "test"
        val expected = Teacher()
        expected.firstName = testStr
        expected.lastName = testStr
        expected.degree = testStr
        expected.firstDay = LocalDate.now()
        expected.birthDay = LocalDate.now()
        expected.rank = testStr
        expected.title = testStr
        expected.salary = 1111

        repository.save(expected).block()

        expected.id?.let { repository.findById(it) }?.let {
            StepVerifier.create(it)
                .expectNextMatches { it.equals(expected) }
                .verifyComplete()
        }
    }

    @Test
    fun save_shouldChangeTeacher_whenTeacherExists() {
        val expected = repository.findAll().blockFirst()
        expected.lastName = "test"

        repository.save(expected).block()

        expected.id?.let { repository.findById(it) }?.let {
            StepVerifier.create(it)
                .expectNextMatches { it.equals(expected) }
                .verifyComplete()
        }
    }

    @Test
    fun delete_shouldDeleteTeacher_whenTeacherExists() {
        val teacher = repository.findAll().blockFirst()
        val expectedSize = 1

        repository.delete(teacher).block()

        StepVerifier.create(repository.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }

    @Test
    open fun delete_shouldNotDeleteTeachers_whenTeacherNotExists() {
        val testStr = "test"
        val teacher = Teacher()
        teacher.firstName = testStr
        teacher.lastName = testStr
        teacher.degree = testStr
        teacher.firstDay = LocalDate.now()
        teacher.birthDay = LocalDate.now()
        teacher.rank = testStr
        teacher.title = testStr
        teacher.salary = 1111
        val expectedSize = 2

        repository.delete(teacher).block()

        StepVerifier.create(repository.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }
}
