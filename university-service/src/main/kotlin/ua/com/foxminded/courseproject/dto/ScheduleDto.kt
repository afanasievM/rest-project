package ua.com.foxminded.courseproject.dto

import java.time.LocalDateTime
import java.util.UUID

data class ScheduleDto(
    var id: UUID? = UUID.randomUUID(),
    var weeks: List<WeekScheduleDto>? = mutableListOf(),
    var startDate: LocalDateTime? = null,
    var endDate: LocalDateTime? = null,
)
