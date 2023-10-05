package ua.com.foxminded.courseproject.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalTime
import java.util.UUID


data class LessonDto(
    var id: UUID? = UUID.randomUUID(),
    @field:NotNull(message = "Lesson subject should be not null.")
    var subject: SubjectDto? = null,
    @field:NotNull(message = "Lesson classroom should be not null.")
    var classRoom: ClassRoomDto? = null,
    @field:NotNull
    @field:Min(value = 0, message = "Lesson number should be greater than 0 and less than 5.")
    @field:Max(value = 5, message = "Lesson number should be greater than 0 and less than 5.")
    var number: Int? = null,
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var teacher: TeacherDto? = null,
    var groups: List<GroupDto>? = mutableListOf()
)

