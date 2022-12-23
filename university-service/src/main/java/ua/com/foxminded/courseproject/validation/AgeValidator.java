package ua.com.foxminded.courseproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {
    private long min;

    @Override
    public void initialize(Age constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate birthday, ConstraintValidatorContext context) {
        if (birthday == null) {
            return true;
        }
        long age = LocalDate.from(birthday).until(LocalDate.now(), ChronoUnit.YEARS);
        return age >= min;
    }

}
