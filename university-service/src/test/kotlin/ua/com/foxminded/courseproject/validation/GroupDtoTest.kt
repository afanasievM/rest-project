package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.GroupDto
import validation.Validations.validator

class GroupDtoTest {
    @Test
    fun nameValidation_shouldThrowException_whenNameIsNull() {
        val groupDto = GroupDto()
        groupDto.name = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldThrowException_whenNameIsEmpty() {
        val groupDto = GroupDto()
        groupDto.name = ""
        val expectedMessage = "Group name should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        val groupDto = GroupDto()
        groupDto.name = "s"
        val expectedSize = 0

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthBetweenOneAndThirtySix() {
        val groupDto = GroupDto()
        groupDto.name = "sss"
        val expectedSize = 0

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        val groupDto = GroupDto()
        groupDto.name = "s".repeat(36)
        val expectedSize = 0

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun nameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        val groupDto = GroupDto()
        groupDto.name = "s".repeat(37)
        val expectedMessage = "Group name should be between 1 and 36."
        val expectedSize = 1

        val constraintViolations = validator.validate(groupDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }
}
