package ua.com.foxminded.courseproject.validation

import java.time.LocalDate
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.TeacherDto
import validation.ValidationTestFixture.Companion.validator

class TeacherDtoTest : PersonDtoTest<TeacherDto>() {
    @BeforeEach
    fun setUp() {
        person = TeacherDto()
        person.firstName = "firstName"
        person.lastName = "lastName"
        person.birthDay = LocalDate.now().minusYears(18)
        person.firstDay = LocalDate.now()
        person.degree = "degree"
        person.salary = 1000
        person.rank = "rank"
        person.title = "title"
    }

    @Test
    fun courseValidation_shouldThrowException_whenNumberIsNull() {
        person.firstDay = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }
}
