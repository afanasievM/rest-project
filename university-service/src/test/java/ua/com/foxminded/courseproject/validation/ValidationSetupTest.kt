package ua.com.foxminded.courseproject.validation

import org.junit.jupiter.api.BeforeAll
import javax.validation.Validation
import javax.validation.Validator

open class ValidationSetupTest {
    companion object {

        @JvmStatic
        protected lateinit var validator: Validator

        @JvmStatic
        @BeforeAll
        fun setUpValidator() {
            val factory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }
}
