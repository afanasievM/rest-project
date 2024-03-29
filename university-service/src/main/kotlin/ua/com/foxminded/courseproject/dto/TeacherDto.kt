package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import org.springframework.format.annotation.DateTimeFormat

data class TeacherDto(
    var degree: String? = null,
    var salary: Int? = null,
    var rank: String? = null,
    var title: String? = null
) : PersonDto() {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull
    var firstDay: LocalDate? = null
    override fun toString(): String {
        return "TeacherDto(id=$id, firstName=$firstName, lastName=$lastName, birthDay=$birthDay, " +
                "degree=$degree, salary=$salary, rank=$rank, title=$title, firstDay=$firstDay)"
    }
}
