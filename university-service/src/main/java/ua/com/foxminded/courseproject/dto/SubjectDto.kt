package ua.com.foxminded.courseproject.dto

import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class SubjectDto(
    var id: UUID? = null,
    @field:NotNull
    @field:Size(min = 1, max = 36, message = "Name of subject length should be between 1 and 36.")
    var name: String? = null
)
