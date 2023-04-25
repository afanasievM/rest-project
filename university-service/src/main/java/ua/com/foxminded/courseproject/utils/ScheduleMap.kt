package ua.com.foxminded.courseproject.utils

import com.fasterxml.jackson.annotation.JsonIgnore
import io.swagger.v3.oas.annotations.media.Schema
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import java.time.LocalDate

@Schema(description = "Response-Object Map<LocalDate, DayScheduleDto).")
class ScheduleMap : HashMap<LocalDate?, DayScheduleDto?>() {
    @JsonIgnore
    override fun isEmpty(): Boolean {
        return super.isEmpty()
    }
}
