package ua.com.foxminded.courseproject.dto

import java.time.LocalDateTime
import java.util.*

data class ScheduleDto(
    var id: UUID? = null,
    var weeks: List<WeekScheduleDto>? = null,
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
)
