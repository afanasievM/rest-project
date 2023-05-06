package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.sql.Timestamp
import java.util.*

@Document("schedule")
data class Schedule(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = null,

    @DBRef
    @Field("weeks")
    var weeks: MutableList<WeekSchedule> = mutableListOf(),

    @Field("start_time")
    var startDate: Timestamp? = null,

    @Field("end_time")
    var endDate: Timestamp? = null
)