package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.dto.StudentDto
import java.time.LocalDate
import javax.validation.ConstraintViolation

class StudentDtoTest : PersonDtoTest<StudentDto>() {
    @BeforeEach
    fun setUp() {
        person = StudentDto()
        person.firstName = "firstName"
        person.lastName = "lastName"
        person.birthDay = LocalDate.now().minusYears(18)
        person.group = GroupDto()
        person.course = 4
        person.captain = false
    }

    @Test
    fun courseValidation_shouldThrowException_whenNumberIsNull() {
        person.course = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<StudentDto>> = validator.validate<StudentDto>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun courseValidation_shouldThrowException_whenNumberLessOne() {
        person.course = 0
        val expectedMessage = "Student course number be greater than or equals 1 and less or equals than 6."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<StudentDto>> = validator.validate<StudentDto>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun courseValidation_shouldNotThrowException_whenNumberGreaterSix() {
        person.course = 7
        val expectedMessage = "Student course number be greater than or equals 1 and less or equals than 6."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<StudentDto>> = validator.validate<StudentDto>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun courseValidation_shouldNotThrowException_whenNumberEqualsOne() {
        person.course = 1
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<StudentDto>> = validator.validate<StudentDto>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun courseValidation_shouldNotThrowException_whenNumberEqualsFive() {
        person.course = 5
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<StudentDto>> = validator.validate<StudentDto>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }
}
