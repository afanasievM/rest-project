package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.util.Streamable
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*

@SpringBootTest
@DataSet(value = ["users.json"], cleanBefore = true, cleanAfter = true)
@Transactional
internal open class TeacherRepositoryTest {
    @Autowired
    private lateinit var repository: TeacherRepository

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
        val expected = Teacher()
        expected.firstName = testStr
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
    fun delete_shouldNotDeleteTeachers_whenTeacherNotExists() {
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

        repository.delete(teacher)
        val teachers = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, teachers.size)
    }
}