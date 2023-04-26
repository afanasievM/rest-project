package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.PersonDto
import java.time.LocalDate
import javax.validation.ConstraintViolation

abstract class PersonDtoTest<T : PersonDto> : ValidationSetupTest() {
    protected lateinit var person: T

    @Test
    fun firstnameValidation_shouldThrowException_whenNameIsNull() {
        person.firstName = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun firstnameValidation_shouldThrowException_whenNameIsEmpty() {
        person.firstName = ""
        val expectedMessage = "Firstname should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        println(constraintViolations.size)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun firstnameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        person.firstName = "s"
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun firstnameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        person.firstName = "s".repeat(36)
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun firstnameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        person.firstName = "s".repeat(37)
        val expectedMessage = "Firstname should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)

        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun lastnameValidation_shouldThrowException_whenNameIsNull() {
        person.lastName = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun lastnameValidation_shouldThrowException_whenNameIsEmpty() {
        person.lastName = ""
        val expectedMessage = "Lastname should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun lastnameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        person.lastName = "s"
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun lastnameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        person.lastName = "s".repeat(36)
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun lastnameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        person.lastName = "s".repeat(37)
        val expectedMessage = "Lastname should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun birthDayValidation_shouldThrowException_whenBirthDayIsNull() {
        person.birthDay = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun birthDayValidation_shouldThrowException_whenAgeLessSixteen() {
        person.birthDay = LocalDate.now().minusYears(15)
        val expectedMessage = "Age of person should be greater than 16"
        val expectedSize = 1

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun birthDayValidation_shouldNotThrowException_whenAgeEqualsSixteen() {
        person.birthDay = LocalDate.now().minusYears(16)
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun validation_shouldNotThrowException_whenParametersAreValid() {
        val expectedSize = 0

        val constraintViolations: Set<ConstraintViolation<T>> = validator.validate<T>(person)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }
}
