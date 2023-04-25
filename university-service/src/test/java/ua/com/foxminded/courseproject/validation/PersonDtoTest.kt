package ua.com.foxminded.courseproject.validation;

import org.junit.jupiter.api.Test;
import ua.com.foxminded.courseproject.dto.PersonDto;

import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class PersonDtoTest<T extends PersonDto> extends ValidationSetupTest {

    protected T person;

    @Test
    public void firstnameValidation_shouldThrowException_whenNameIsNull() {
        this.person.setFirstName(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void firstnameValidation_shouldThrowException_whenNameIsEmpty() {
        this.person.setFirstName("");
        String expectedMessage = "Firstname should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void firstnameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        this.person.setFirstName("s");
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }


    @Test
    public void firstnameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        this.person.setFirstName("s".repeat(36));
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void firstnameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        this.person.setFirstName("s".repeat(37));
        String expectedMessage = "Firstname should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void lastnameValidation_shouldThrowException_whenNameIsNull() {
        this.person.setLastName(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void lastnameValidation_shouldThrowException_whenNameIsEmpty() {
        this.person.setLastName("");
        String expectedMessage = "Lastname should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void lastnameValidation_shouldNotThrowException_whenNameLengthEqualsOne() {
        this.person.setLastName("s");
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }


    @Test
    public void lastnameValidation_shouldNotThrowException_whenNameLengthEqualsThirtySix() {
        this.person.setLastName("s".repeat(36));
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void lastnameValidation_shouldThrowException_whenNameLengthGreaterThanThirtySix() {
        this.person.setLastName("s".repeat(37));
        String expectedMessage = "Lastname should be between 1 and 36.";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void birthDayValidation_shouldThrowException_whenBirthDayIsNull() {
        this.person.setBirthDay(null);
        String expectedMessage = "must not be null";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }


    @Test
    public void birthDayValidation_shouldThrowException_whenAgeLessSixteen() {
        this.person.setBirthDay(LocalDate.now().minusYears(15));
        String expectedMessage = "Age of person should be greater than 16";
        Integer expectedSize = 1;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();
        String actualMessage = constraintViolations.iterator().next().getMessage();

        assertEquals(expectedMessage, actualMessage);
        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void birthDayValidation_shouldNotThrowException_whenAgeEqualsSixteen() {
        this.person.setBirthDay(LocalDate.now().minusYears(16));
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

    @Test
    public void validation_shouldNotThrowException_whenParametersAreValid() {
        Integer expectedSize = 0;

        Set<ConstraintViolation<T>> constraintViolations = validator.validate(this.person);
        Integer actualSize = constraintViolations.size();

        assertEquals(expectedSize, actualSize);
    }

}
