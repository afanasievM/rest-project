package ua.com.foxminded.courseproject.exceptions

import ua.com.foxminded.courseproject.dto.StudentDto

class StudentConflictException : RuntimeException {
    constructor() : super()
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(s: String) : super(s)
    constructor(studentDto: StudentDto) : super(
        Companion.message.format(
            studentDto.firstName,
            studentDto.lastName,
            studentDto.birthDay.toString()
        )
    )

    companion object {
        private const val message = "Student %s %s %s already exists."
    }
}
