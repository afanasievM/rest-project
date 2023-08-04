package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDate
import java.util.*

@Document(collection = "students")
data class Student(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    override var id: UUID? = UUID.randomUUID(),

    @Field("firstname")
    override var firstName: String? = null,

    @Field("lastname")
    override var lastName: String? = null,

    @Field("birthday")
    override var birthDay: LocalDate? = null,

//    @DBRef
    @Field("group_id")
    var group: Group? = null,

    @Field("course")
    var course: Int? = null,

    @Field("is_captain")
    var captain: Boolean? = null
) : Person()
