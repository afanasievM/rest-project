package ua.com.foxminded.courseproject.dto

import java.util.UUID
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class GroupDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull
    @field:Size(min = 1, max = 36, message = "Group name should be between 1 and 36.")
    var name: String? = null
)
