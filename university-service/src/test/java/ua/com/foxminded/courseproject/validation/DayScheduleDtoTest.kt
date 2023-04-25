package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayScheduleDtoTest extends ValidationSetupTest {

    @Test
    public void dayNumberValidation_shouldThrowException_whenNumberIsNull() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void dayNumberValidation_shouldThrowException_whenNumberLessOne() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(0);
        String expectedMessage = "Weekday number should be greater than 0 and less than 8.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void dayNumberValidation_shouldNotThrowException_whenNumberGreaterSeven() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(8);
        String expectedMessage = "Weekday number should be greater than 0 and less than 8.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void dayNumberValidation_shouldNotThrowException_whenNumberEqualsOne() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(1);
        Integer expectedSize = 0;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void dayNumberValidation_shouldNotThrowException_whenNumberEqualsSeven() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(7);
        Integer expectedSize = 0;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void dayNumberValidation_shouldNotThrowException_whenNumberBetweenOneAndSeven() {
        DayScheduleDto dayScheduleDto = new DayScheduleDto();
        dayScheduleDto.setDayNumber(5);
        Integer expectedSize = 0;

        Set<ConstraintViolation<DayScheduleDto>> constraintViolations = validator.validate(dayScheduleDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }
}
