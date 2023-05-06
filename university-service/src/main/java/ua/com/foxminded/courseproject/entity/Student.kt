package ua.com.foxminded.courseproject.entity

import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document("students")
data class Student(
    @DBRef
    @Field("group_id")
    var group: Group? = null,

    @Field("course")
    var course: Int? = null,

    @Field("is_captain")
    var captain: Boolean? = null
) : Person()
