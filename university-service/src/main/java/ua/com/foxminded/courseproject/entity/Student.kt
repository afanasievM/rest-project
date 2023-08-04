package ua.com.foxminded.courseproject.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document("students")
data class Student(
    @Id
    override var id: UUID? = UUID.randomUUID(),
    override var firstName: String? = null,
    override var lastName: String? = null,
    override var birthDay: LocalDate? = null,
    var group: Group? = null,
    var course: Int? = null,
    var captain: Boolean? = null
) : Person()
