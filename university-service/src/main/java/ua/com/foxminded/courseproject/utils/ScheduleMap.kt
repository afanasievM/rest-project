package ua.com.foxminded.courseproject.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;

import java.time.LocalDate;
import java.util.HashMap;

@Schema(description = "Response-Object Map<LocalDate, DayScheduleDto).")
public class ScheduleMap extends HashMap<LocalDate, DayScheduleDto> {
    @JsonIgnore
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
