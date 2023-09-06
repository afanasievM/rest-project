package ua.com.foxminded.courseproject.exceptions

import ua.com.foxminded.courseproject.dto.TeacherDto

class TeacherConflictException : RuntimeException {
    constructor() : super()
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(s: String) : super(s)
    constructor(teacherDto: TeacherDto) : super(
        MESSAGE.format(
            teacherDto.firstName,
            teacherDto.lastName,
            teacherDto.birthDay.toString()
        )
    )

    companion object {
        private const val MESSAGE = "Teacher %s %s %s already exists."
    }
}
