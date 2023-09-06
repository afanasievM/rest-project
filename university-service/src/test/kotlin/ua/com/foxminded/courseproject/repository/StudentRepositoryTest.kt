package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Student
import ua.com.foxminded.courseproject.mapper.GroupMapper
import ua.com.foxminded.courseproject.mapper.StudentMapper
import java.time.LocalDate
import java.util.*


@DataMongoTest
@Import(StudentRepositoryImp::class, StudentMapper::class, GroupMapper::class)
internal open class StudentRepositoryTest : DBTestConfig() {

    @Autowired
    private lateinit var repository: StudentRepository

    @Test
    fun findAll_shouldReturnFluxStudents() {
        val expectedSize = 2

        val students = repository.findAll()

        StepVerifier.create(students)
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }

    @Test
    fun findById_shouldReturnStudent_whenIdExists() {
        val id = UUID.fromString("f92afb9e-462a-11ed-b878-0242ac120002")
        val expectedFirstname = "Yura"

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
    fun save_shouldAddStudent_whenStudentNotExists() {
        val testStr = "test"
        val expected = Student()
        val group = Group()
        group.id = UUID.fromString("4937378e-4620-11ed-b878-0242ac120002")
        group.name = "ET-01"
        expected.firstName = testStr
        expected.lastName = testStr
        expected.group = group
        expected.birthDay = LocalDate.now()
        expected.course = 4
        expected.captain = false

        repository.save(expected).block()


        StepVerifier.create(repository.findById(expected.id!!))
            .expectNextMatches { it.firstName == expected.firstName }
            .verifyComplete()

    }


    @Test
    fun save_shouldChangeStudent_whenStudentExists() {
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
    fun delete_shouldDeleteStudent_whenStudentExists() {
        val student = repository.findAll().blockFirst()
        val expectedSize = 1

        repository.delete(student).block()

        StepVerifier.create(repository.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }

    @Test
    fun delete_shouldNotDeleteStudents_whenStudentNotExists() {
        val testStr = "test"
        val student = Student()
        val group = Group()
        group.id = UUID.fromString("4937378e-4620-11ed-b878-0242ac120002")
        group.name = "ET-01"
        student.firstName = testStr
        student.lastName = testStr
        student.group = group
        student.birthDay = LocalDate.now()
        student.course = 4
        student.captain = false
        val expectedSize = 2

        repository.delete(student).block()

        StepVerifier.create(repository.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }
}
