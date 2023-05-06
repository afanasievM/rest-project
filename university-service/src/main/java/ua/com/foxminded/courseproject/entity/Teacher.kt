package ua.com.foxminded.courseproject.entity

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDate
import java.util.*

@Document("teachers")
data class Teacher(

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

