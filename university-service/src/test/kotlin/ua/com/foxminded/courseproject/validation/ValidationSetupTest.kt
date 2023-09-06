package ua.com.foxminded.courseproject.validation

import javax.validation.Validation
import javax.validation.Validator

open class ValidationSetupTest {

    protected var validator: Validator = Validation.buildDefaultValidatorFactory().validator

}
