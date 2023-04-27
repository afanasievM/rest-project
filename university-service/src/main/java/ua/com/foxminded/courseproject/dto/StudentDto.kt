package ua.com.foxminded.courseproject.dto

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class StudentDto(
    var group: GroupDto? = null,
    @field:NotNull
    @field:Min(value = 1, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    @field:Max(value = 6, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    var course: Int? = null,
    var captain: Boolean? = null

) : PersonDto()
