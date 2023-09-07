package validation

import javax.validation.Validation
import javax.validation.Validator

class ValidationTestFixture {

    companion object {
        val validator: Validator = Validation.buildDefaultValidatorFactory().validator
    }

}
