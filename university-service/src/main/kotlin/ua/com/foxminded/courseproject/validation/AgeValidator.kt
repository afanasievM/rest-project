package ua.com.foxminded.courseproject.validation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class AgeValidator : ConstraintValidator<Age, LocalDate?> {
    private var min: Long = 0
    override fun initialize(constraintAnnotation: Age) {
        super.initialize(constraintAnnotation)
        this.min = constraintAnnotation.min
    }

    override fun isValid(birthday: LocalDate?, context: ConstraintValidatorContext): Boolean {
        if (birthday == null) {
            return true
        }
        val age = LocalDate.from(birthday).until(LocalDate.now(), ChronoUnit.YEARS)
        return age >= min
    }
}
