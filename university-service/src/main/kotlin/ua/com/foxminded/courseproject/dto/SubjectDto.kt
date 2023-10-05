package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.util.UUID

data class SubjectDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull
    @field:Size(min = 1, max = 36, message = "Name of subject length should be between 1 and 36.")
    var name: String? = null
)
