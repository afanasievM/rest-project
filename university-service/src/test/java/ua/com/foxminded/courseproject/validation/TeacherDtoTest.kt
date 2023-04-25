package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.TeacherDto;

import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherDtoTest extends PersonDtoTest<TeacherDto> {

    @BeforeEach
    void setUp() {
        this.person = new TeacherDto();
        this.person.setFirstName("firstName");
        this.person.setLastName("lastName");
        this.person.setBirthDay(LocalDate.now().minusYears(18));
        this.person.setFirstDay(LocalDate.now());
        this.person.setDegree("degree");
        this.person.setSalary(1000);
        this.person.setRank("rank");
        this.person.setTitle("title");
    }

    @Test
    public void courseValidation_shouldThrowException_whenNumberIsNull() {
        this.person.setFirstDay(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<TeacherDto>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

}
