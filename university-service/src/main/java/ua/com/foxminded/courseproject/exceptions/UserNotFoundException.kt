package ua.com.foxminded.courseproject.exceptions

class UserNotFoundException : NoSuchElementException {
    constructor() : super()
    constructor(s: String, cause: Throwable) : super(s, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(s: String) : super(Companion.message.format(s))

    companion object {
        private const val message = "Can't find user with username - '%s'"
    }
}
