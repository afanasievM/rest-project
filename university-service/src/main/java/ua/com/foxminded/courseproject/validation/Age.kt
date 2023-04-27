package ua.com.foxminded.courseproject.validation

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [AgeValidator::class])
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class Age(val message: String = "{minAge.message}",
                     val groups: Array<KClass<*>> = [],
                     val payload: Array<KClass<out Payload>> = [],
                     val min: Long = 18)
