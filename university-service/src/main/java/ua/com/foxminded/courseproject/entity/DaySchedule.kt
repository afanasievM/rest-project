package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.util.*


@Document("day_schedule")
data class DaySchedule(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = null,

    @DBRef
    var lessons: MutableList<Lesson> = mutableListOf(),

    @Field("day_number")
    var dayNumber: Int? = null

)
