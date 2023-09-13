package validation

import javax.validation.Validation
import javax.validation.Validator

object Validations {
    val validator: Validator = Validation.buildDefaultValidatorFactory().validator
}
