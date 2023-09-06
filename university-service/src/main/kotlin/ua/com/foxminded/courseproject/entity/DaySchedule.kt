package ua.com.foxminded.courseproject.entity

import java.util.UUID


data class DaySchedule(
    var id: UUID? = UUID.randomUUID(),
    var lessons: MutableList<Lesson> = mutableListOf(),
    var dayNumber: Int? = null
)
