package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.ClassRoomDto;
import ua.com.foxminded.courseproject.dto.LessonDto;
import ua.com.foxminded.courseproject.dto.SubjectDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LessonDtoTest extends ValidationSetupTest {
    private LessonDto lessonDto;

    @BeforeEach
    void setUp(){
        this.lessonDto = new LessonDto();
        this.lessonDto.setSubject(new SubjectDto());
        this.lessonDto.setClassRoom(new ClassRoomDto());
        this.lessonDto.setNumber(3);
    }

    @Test
    public void subjectValidation_shouldThrowException_whenNameIsNull() {
        this.lessonDto.setSubject(null);
        String expectedMessage = "Lesson subject should be not null.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void validation_shouldNotThrowException_whenSubjectClassroomNumberAreNotNullAndValid() {
        Integer expectedSize = 0;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void classRoomValidation_shouldThrowException_whenNameIsNull() {
        this.lessonDto.setClassRoom(null);
        String expectedMessage = "Lesson classroom should be not null.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }
    
    @Test
    public void numberValidation_shouldThrowException_whenNumberIsNull() {
        this.lessonDto.setNumber(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldThrowException_whenNumberLessZero() {
        this.lessonDto.setNumber(-1);
        String expectedMessage = "Lesson number should be greater than 0 and less than 5.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberGreaterFive() {
        this.lessonDto.setNumber(6);
        String expectedMessage = "Lesson number should be greater than 0 and less than 5.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberEqualsZero() {
        this.lessonDto.setNumber(0);
        Integer expectedSize = 0;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberEqualsFive() {
        this.lessonDto.setNumber(5);
        Integer expectedSize = 0;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void numberValidation_shouldNotThrowException_whenNumberBetweenZeroAndFive() {
        this.lessonDto.setNumber(3);
        Integer expectedSize = 0;

        Set<ConstraintViolation<LessonDto>> constraintViolations = validator.validate(lessonDto);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

}
