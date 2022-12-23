package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.ClassRoomDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClassRoomDtoTest extends ValidationSetupTest {

    @Test
    public void numberValidation_shouldThrowException_whenNumberIsNull() {
        ClassRoomDto classRoomDto = new ClassRoomDto();
        classRoomDto.setNumber(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<ClassRoomDto>> constraintViolations = validator.validate(classRoomDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldThrowException_whenNumberIsNegative() {
        ClassRoomDto classRoomDto = new ClassRoomDto();
        classRoomDto.setNumber(-5);
        String expectedMessage = "Classroom number must be positive or equal 0.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<ClassRoomDto>> constraintViolations = validator.validate(classRoomDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        ClassRoomDto classRoomDto = new ClassRoomDto();
        classRoomDto.setNumber(0);
        Integer expectedSize = 0;

        Set<ConstraintViolation<ClassRoomDto>> constraintViolations = validator.validate(classRoomDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberIsPositive() {
        ClassRoomDto classRoomDto = new ClassRoomDto();
        classRoomDto.setNumber(1);
        Integer expectedSize = 0;

        Set<ConstraintViolation<ClassRoomDto>> constraintViolations = validator.validate(classRoomDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }


}
