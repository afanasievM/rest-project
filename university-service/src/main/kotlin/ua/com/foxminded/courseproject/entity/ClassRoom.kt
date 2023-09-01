package ua.com.foxminded.courseproject.entity


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.util.*

@Document("classrooms")
data class ClassRoom(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = UUID.randomUUID(),
    @Field("number")
    var number: Int? = null
)