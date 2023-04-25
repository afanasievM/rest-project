package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.WeekScheduleDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeekScheduleDtoTest extends ValidationSetupTest {

    @Test
    public void numberValidation_shouldThrowException_whenNumberIsNull() {
        WeekScheduleDto weekScheduleDto = new WeekScheduleDto();
        weekScheduleDto.setIsOdd(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<WeekScheduleDto>> constraintViolations = validator.validate(weekScheduleDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }


    @Test
    public void numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        WeekScheduleDto weekScheduleDto = new WeekScheduleDto();
        weekScheduleDto.setIsOdd(true);
        Integer expectedSize = 0;

        Set<ConstraintViolation<WeekScheduleDto>> constraintViolations = validator.validate(weekScheduleDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

}
