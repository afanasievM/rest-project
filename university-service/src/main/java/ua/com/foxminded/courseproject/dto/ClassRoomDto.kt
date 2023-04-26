package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class ClassRoomDto(
    var id: UUID? = null,
    var number:
    @NotNull
    @PositiveOrZero(message = "Classroom number must be positive or equal 0.")
    Int? = null
)
