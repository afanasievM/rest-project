package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ua.com.foxminded.courseproject.dto.WeekScheduleDto

class WeekScheduleDtoTest : ValidationSetupTest() {
    @Test
    fun numberValidation_shouldThrowException_whenNumberIsNull() {
        val weekScheduleDto = WeekScheduleDto()
        weekScheduleDto.isOdd = null
        val expectedMessage = "must not be null"
        val expectedSize = 1
        val constraintViolations = validator.validate(weekScheduleDto)
        val actualSize = constraintViolations.size
        val actualMessage = constraintViolations.iterator().next().message
        Assertions.assertEquals(expectedMessage, actualMessage)
        Assertions.assertEquals(expectedSize, actualSize)
    }

    @Test
    fun numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        val weekScheduleDto = WeekScheduleDto()
        weekScheduleDto.isOdd = true
        val expectedSize = 0
        val constraintViolations = validator.validate(weekScheduleDto)
        val actualSize = constraintViolations.size
        Assertions.assertEquals(expectedSize, actualSize)
    }
}
