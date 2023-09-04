package ua.com.foxminded.courseproject.dto

import org.springframework.format.annotation.DateTimeFormat
import ua.com.foxminded.courseproject.validation.Age
import java.time.LocalDate
import java.util.UUID
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class PersonDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull
    @field:Size(min = 1, max = 36, message = "Firstname should be between 1 and 36.")
    var firstName: String? = null,
    @field:NotNull
    @field:Size(min = 1, max = 36, message = "Lastname should be between 1 and 36.")
    var lastName: String? = null,

    @field:Age(min = 16)
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    @field:NotNull
    var birthDay: LocalDate? = null
)

