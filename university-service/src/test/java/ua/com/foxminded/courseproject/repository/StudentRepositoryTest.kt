package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.util.Streamable
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Student
import java.time.LocalDate
import java.util.*

@DataMongoTest
internal open class StudentRepositoryTest : DBTestConfig() {
    @Autowired
    private lateinit var repository: StudentRepository

    @Test
    fun findAll_shouldReturnListStudents() {
        val expectedSize = 2

        val students = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, students.size)
    }

    @Test
    fun findById_shouldReturnStudent_whenIdExists() {
        val id = UUID.fromString("f92afb9e-462a-11ed-b878-0242ac120002")
        val expectedFirstname = "Yura"

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

        repository.save(expected)

        Assertions.assertEquals(expected, expected.id?.let { repository.findById(it).get() })
    }

    @Test
    fun save_shouldChangeStudent_whenStudentExists() {
        val expected = Streamable.of(repository.findAll()).stream().findAny().get()
        expected.lastName = "test"

        repository.save(expected)

        Assertions.assertEquals(expected, expected.id?.let { repository.findById(it).get() })
    }

    @Test
    fun delete_shouldDeleteStudent_whenStudentExists() {
        val student = Streamable.of(repository.findAll()).stream().findAny().get()
        val expectedSize = 1

        repository.delete(student)
        val students = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, students.size)
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

        repository.delete(student)
        val students = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, students.size)
    }
}