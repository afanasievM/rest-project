package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull


data class StudentDto(
    var group: GroupDto? = null,
    @field:NotNull
    @field:Min(value = 1, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    @field:Max(value = 6, message = "Student course number be greater than or equals 1 and less or equals than 6.")
    var course: Int? = null,
    var captain: Boolean? = null

) : PersonDto() {
    override fun toString(): String {
        return "StudentDto(id=$id, firstName=$firstName, lastName=$lastName, birthDay=$birthDay, group=$group," +
                " course=$course, captain=$captain)"
    }
}
