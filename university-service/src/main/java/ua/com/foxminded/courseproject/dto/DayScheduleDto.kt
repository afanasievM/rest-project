package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class DayScheduleDto(
    var id: UUID? = null,
    var lessons: List<LessonDto>? = null,
    @field:NotNull
    @field:Min(value = 1, message = "Weekday number should be greater than 0 and less than 8.")
    @field:Max(value = 7, message = "Weekday number should be greater than 0 and less than 8.")
    var dayNumber: Int? = null
)
