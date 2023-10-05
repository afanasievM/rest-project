package validation

import jakarta.validation.Validation
import jakarta.validation.Validator


object Validations {
    val validator: Validator = Validation.buildDefaultValidatorFactory().validator
}
