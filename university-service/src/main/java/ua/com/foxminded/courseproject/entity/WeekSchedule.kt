package ua.com.foxminded.courseproject.entity


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.util.*

@Document("weeks")
data class WeekSchedule(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = null,

    @DBRef
    @Field("days")
    var daysSchedule: MutableList<DaySchedule> = mutableListOf(),

    @Field("odd")
    var isOdd: Boolean? = null
)
