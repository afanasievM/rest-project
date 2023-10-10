package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.util.UUID


data class DayScheduleDto(
    var id: UUID? = UUID.randomUUID(),
    var lessons: List<LessonDto>? = mutableListOf(),
    @field:NotNull
    @field:Min(value = 1, message = "Weekday number should be greater than 0 and less than 8.")
    @field:Max(value = 7, message = "Weekday number should be greater than 0 and less than 8.")
    var dayNumber: Int? = null
)
