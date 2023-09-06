package ua.com.foxminded.courseproject.entity


import java.time.LocalTime
import java.util.UUID

data class Lesson(
    var id: UUID? = UUID.randomUUID(),
    var subject: Subject? = null,
    var classRoom: ClassRoom? = null,
    var number: Int? = null,
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var teacher: Teacher? = null,
    var groups: MutableList<Group> = mutableListOf()
)
