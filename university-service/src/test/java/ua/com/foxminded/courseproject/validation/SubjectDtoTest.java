package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.SubjectDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubjectDtoTest extends ValidationSetupTest {

    @Test
    public void nameValidation_shouldThrowException_whenNameIsNull() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldThrowException_whenNameIsEmpty() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("");
        String expectedMessage = "Name of subject length should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("s");
        Integer expectedSize = 0;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthBetweenOneAndThirtySix() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("sss");
        Integer expectedSize = 0;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("s".repeat(36));
        Integer expectedSize = 0;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void nameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setName("s".repeat(37));
        String expectedMessage = "Name of subject length should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<SubjectDto>> constraintViolations = validator.validate(subjectDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

}
