package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalDate
import java.util.*

@Document(collection = "teachers")
data class Teacher(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    override var id: UUID? = UUID.randomUUID(),

    @Field("firstname")
    override var firstName: String? = null,

    @Field("lastname")
    override var lastName: String? = null,

    @Field("birthday")
    override var birthDay: LocalDate? = null,

    @Field("degree")
    var degree: String? = null,

    @Field("salary")
    var salary: Int? = null,

    @Field("first_date")
    var firstDay: LocalDate? = null,

    @Field("rank")
    var rank: String? = null,

    @Field("title")
    var title: String? = null
) : Person()

