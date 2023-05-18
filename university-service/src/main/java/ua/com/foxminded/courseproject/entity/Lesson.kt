package ua.com.foxminded.courseproject.entity


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.time.LocalTime
import java.util.*

@Document("lessons")
data class Lesson(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = UUID.randomUUID(),

    @DBRef
    @Field("subject")
    var subject: Subject? = null,

    @DBRef
    @Field("classroom")
    var classRoom: ClassRoom? = null,

    @Field("number")
    var number: Int? = null,

    @Field("start_time")
    var startTime: LocalTime? = null,

    @Field("end_time")
    var endTime: LocalTime? = null,

    @DBRef
    @Field("teacher")
    var teacher: Teacher? = null,

    @DBRef
    @Field("groups")
    var groups: MutableList<Group> = mutableListOf()
)
