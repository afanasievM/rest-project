package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.util.UUID


data class ClassRoomDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull
    @field:PositiveOrZero(message = "Classroom number must be positive or equal 0.")
    var number: Int? = null
)
