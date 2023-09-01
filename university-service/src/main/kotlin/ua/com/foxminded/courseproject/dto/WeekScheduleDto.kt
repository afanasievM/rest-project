package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull

data class WeekScheduleDto(
    var id: UUID? = UUID.randomUUID(),
    var daysSchedule: List<DayScheduleDto>? = mutableListOf(),
    @field:NotNull
    var isOdd: Boolean? = null
)
