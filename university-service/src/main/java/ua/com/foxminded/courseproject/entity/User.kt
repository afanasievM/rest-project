package ua.com.foxminded.courseproject.entity

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import ua.com.foxminded.courseproject.enums.Role
import java.util.*

@Document("users")
data class User(

    @Field("username")
    var username: String? = null,

    @Field("password")
    var password: String? = null,

    @Field("person_id")
    @DBRef
    var person: Person? = null,

    @Field("role")
    var role: Role? = null
)


