package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.NotNull
import java.util.UUID

data class WeekScheduleDto(
    var id: UUID? = UUID.randomUUID(),
    var daysSchedule: List<DayScheduleDto>? = mutableListOf(),
    @field:NotNull
    var isOdd: Boolean? = null
)
