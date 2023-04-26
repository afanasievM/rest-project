package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull

class WeekScheduleDto(
    var id: UUID? = null,
    var daysSchedule: List<DayScheduleDto>? = null,
    var isOdd: @NotNull Boolean? = null
)
