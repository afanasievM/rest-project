package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.SubjectDto
import validation.ValidationTestFixture.Companion.validator

class SubjectDtoTest {
    @Test
    fun nameValidation_shouldThrowException_whenNameIsNull() {
        val subjectDto = SubjectDto()
        subjectDto.name = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldThrowException_whenNameIsEmpty() {
        val subjectDto = SubjectDto()
        subjectDto.name = ""
        val expectedMessage = "Name of subject length should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        val subjectDto = SubjectDto()
        subjectDto.name = "s"
        val expectedSize = 0

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthBetweenOneAndThirtySix() {
        val subjectDto = SubjectDto()
        subjectDto.name = "sss"
        val expectedSize = 0

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        val subjectDto = SubjectDto()
        subjectDto.name = "s".repeat(36)
        val expectedSize = 0

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        val subjectDto = SubjectDto()
        subjectDto.name = "s".repeat(37)
        val expectedMessage = "Name of subject length should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations = validator.validate(subjectDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }
}
