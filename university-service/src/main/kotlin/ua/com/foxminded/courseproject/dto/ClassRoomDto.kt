package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero

data class ClassRoomDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull
    @field:PositiveOrZero(message = "Classroom number must be positive or equal 0.")
    var number: Int? = null
)
