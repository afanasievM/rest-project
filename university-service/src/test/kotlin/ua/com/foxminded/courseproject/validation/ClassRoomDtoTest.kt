package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.ClassRoomDto
import validation.ValidationTestFixture.Companion.validator

class ClassRoomDtoTest {
    @Test
    fun numberValidation_shouldThrowException_whenNumberIsNull() {
        val classRoomDto = ClassRoomDto()
        classRoomDto.number = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(classRoomDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldThrowException_whenNumberIsNegative() {
        val classRoomDto = ClassRoomDto()
        classRoomDto.number = -5
        val expectedMessage = "Classroom number must be positive or equal 0."
        val expectedSize = 1

        val constraintViolations = validator.validate(classRoomDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        val classRoomDto = ClassRoomDto()
        classRoomDto.number = 0
        val expectedSize = 0

        val constraintViolations = validator.validate(classRoomDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberIsPositive() {
        val classRoomDto = ClassRoomDto()
        classRoomDto.number = 1
        val expectedSize = 0

        val constraintViolations = validator.validate(classRoomDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }
}
