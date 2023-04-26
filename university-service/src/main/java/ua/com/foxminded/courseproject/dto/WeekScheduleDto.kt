package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull

data class WeekScheduleDto(
    var id: UUID? = null,
    var daysSchedule: List<DayScheduleDto>? = null,
    @field:NotNull
    var isOdd: Boolean? = null
)
