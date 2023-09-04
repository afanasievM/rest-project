package ua.com.foxminded.courseproject.exceptions

import java.util.UUID

class StudentNotFoundException : NoSuchElementException {
    constructor() : super()
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(s: String) : super(s)
    constructor(s: UUID) : super(MESSAGE.format(s.toString()))

    companion object {
        private const val MESSAGE = "Can't find student with ID=%s"
    }
}
