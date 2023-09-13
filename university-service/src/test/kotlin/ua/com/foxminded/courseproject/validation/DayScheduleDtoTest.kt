package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import validation.Validations.validator

class DayScheduleDtoTest {
    @Test
    fun dayNumberValidation_shouldThrowException_whenNumberIsNull() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = null
        val expectedMessage = "must not be null"
        val expectedSize = 1

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun dayNumberValidation_shouldThrowException_whenNumberLessOne() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = 0
        val expectedMessage = "Weekday number should be greater than 0 and less than 8."
        val expectedSize = 1

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun dayNumberValidation_shouldNotThrowException_whenNumberGreaterSeven() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = 8
        val expectedMessage = "Weekday number should be greater than 0 and less than 8."
        val expectedSize = 1

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message

        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun dayNumberValidation_shouldNotThrowException_whenNumberEqualsOne() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = 1
        val expectedSize = 0

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun dayNumberValidation_shouldNotThrowException_whenNumberEqualsSeven() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = 7
        val expectedSize = 0

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun dayNumberValidation_shouldNotThrowException_whenNumberBetweenOneAndSeven() {
        val dayScheduleDto = DayScheduleDto()
        dayScheduleDto.dayNumber = 5
        val expectedSize = 0

        val constraintViolations = validator.validate(dayScheduleDto)
        val actualSize = constraintViolations.size

        Assertions.assertEquals(expectedSize, actualSize)
    }
}
