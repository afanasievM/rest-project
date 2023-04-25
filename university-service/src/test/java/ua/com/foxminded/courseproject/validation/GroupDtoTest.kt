package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.GroupDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupDtoTest extends ValidationSetupTest {

    @Test
    public void nameValidation_shouldThrowException_whenNameIsNull() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldThrowException_whenNameIsEmpty() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("");
        String expectedMessage = "Group name should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("s");
        Integer expectedSize = 0;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthBetweenOneAndThirtySix() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("sss");
        Integer expectedSize = 0;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("s".repeat(36));
        Integer expectedSize = 0;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        GroupDto groupDto = new GroupDto();
        groupDto.setName("s".repeat(37));
        String expectedMessage = "Group name should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<GroupDto>> constraintViolations = validator.validate(groupDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

}
