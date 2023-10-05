package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate
import java.util.UUID
import org.springframework.format.annotation.DateTimeFormat
import ua.com.foxminded.courseproject.validation.Age


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

