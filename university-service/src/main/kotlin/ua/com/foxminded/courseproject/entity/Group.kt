package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType
import java.util.*

@Document("groups")
data class Group(
    @Id
    @Field("_id", targetType = FieldType.STRING)
    var id: UUID? = UUID.randomUUID(),

    @Field("name")
    var name: String? = null
)
