package ua.com.foxminded.courseproject.dto

import java.time.LocalTime
import java.util.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class LessonDto(
    var id: UUID? = null,
    var subject: @NotNull(message = "Lesson subject should be not null.") SubjectDto? = null,
    var classRoom: @NotNull(message = "Lesson classroom should be not null.") ClassRoomDto? = null,
    var number:
    @NotNull
    @Min(value = 0, message = "Lesson number should be greater than 0 and less than 5.")
    @Max(value = 5, message = "Lesson number should be greater than 0 and less than 5.")
    Int? = null,
    var startTime: LocalTime? = null,
    var endTime: LocalTime? = null,
    var teacher: TeacherDto? = null,
    var groups: List<GroupDto>? = null
)

