package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDate
import java.util.*


abstract class Person(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = UUID.randomUUID(),

    @Field("firstname")
    var firstName: String? = null,

    @Field("lastname")
    var lastName: String? = null,

    @Field("birthday")
    var birthDay: LocalDate? = null
)


