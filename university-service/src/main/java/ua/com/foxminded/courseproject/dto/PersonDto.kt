package ua.com.foxminded.courseproject.dto

import org.springframework.format.annotation.DateTimeFormat
import ua.com.foxminded.courseproject.validation.Age
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

open class PersonDto(
    var id: UUID? = null,
    var firstName:
    @NotNull
    @Size(min = 1, max = 36, message = "Firstname should be between 1 and 36.")
    String? = null,
    var lastName:
    @NotNull
    @Size(min = 1, max = 36, message = "Lastname should be between 1 and 36.")
    String? = null,

    @Age(min = 16)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var birthDay: @NotNull LocalDate? = null
)

