package ua.com.foxminded.courseproject.exceptions

import java.util.*

class StudentNotFoundException : NoSuchElementException {
    constructor() : super()
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(s: String) : super(s)
    constructor(s: UUID) : super(Companion.message.format(s.toString()))

    companion object {
        private const val message = "Can't find student with ID=%s"
    }
}
