package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.GroupDto;
import ua.com.foxminded.courseproject.dto.StudentDto;

import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDtoTest extends PersonDtoTest<StudentDto> {

    @BeforeEach
    void setUp(){
        this.person = new StudentDto();
        this.person.setFirstName("firstName");
        this.person.setLastName("lastName");
        this.person.setBirthDay(LocalDate.now().minusYears(18));
        this.person.setGroup(new GroupDto());
        this.person.setCourse(4);
        this.person.setCaptain(false);
    }

    @Test
    public void courseValidation_shouldThrowException_whenNumberIsNull() {
        this.person.setCourse(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void courseValidation_shouldThrowException_whenNumberLessOne() {
        this.person.setCourse(0);
        String expectedMessage = "Student course number be greater than or equals 1 and less or equals than 6.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void courseValidation_shouldNotThrowException_whenNumberGreaterSix() {
        this.person.setCourse(7);
        String expectedMessage = "Student course number be greater than or equals 1 and less or equals than 6.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void courseValidation_shouldNotThrowException_whenNumberEqualsOne() {
        this.person.setCourse(1);
        Integer expectedSize = 0;

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void courseValidation_shouldNotThrowException_whenNumberEqualsFive() {
        this.person.setCourse(5);
        Integer expectedSize = 0;

        Set<ConstraintViolation<StudentDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

}
