package ua.com.foxminded.courseproject.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import javax.validation.constraints.NotNull

data class TeacherDto(
    var degree: String? = null,
    var salary: Int? = null,
    var rank: String? = null,
    var title: String? = null
) : PersonDto(){
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    var firstDay: LocalDate? = null
}