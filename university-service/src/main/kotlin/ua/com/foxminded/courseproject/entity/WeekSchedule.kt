package ua.com.foxminded.courseproject.entity

import java.util.*

data class WeekSchedule(
    var id: UUID? = UUID.randomUUID(),
    var daysSchedule: MutableList<DaySchedule> = mutableListOf(),
    var isOdd: Boolean? = null
)
