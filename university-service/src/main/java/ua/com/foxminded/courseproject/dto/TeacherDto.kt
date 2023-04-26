package ua.com.foxminded.courseproject.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

class TeacherDto(
    var degree: String? = null,
    var salary: Int? = null,

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var firstDay: @NotNull LocalDate? = null,
    var rank: String? = null,
    var title: String? = null
) : PersonDto()
