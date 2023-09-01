package ua.com.foxminded.courseproject.entity

import java.time.LocalDate
import java.util.*

data class Student(
    override var id: UUID? = UUID.randomUUID(),
    override var firstName: String? = null,
    override var lastName: String? = null,
    override var birthDay: LocalDate? = null,
    var group: Group? = null,
    var course: Int? = null,
    var captain: Boolean? = null
) : Person()
