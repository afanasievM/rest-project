package ua.com.foxminded.restClient.exceptions

import java.util.UUID

class PersonNotFoundException : RuntimeException {
    constructor() : super()
    constructor(s: String?, cause: Throwable?) : super(s, cause)
    constructor(cause: Throwable?) : super(cause)
    constructor(s: String?) : super(s)
    constructor(uuid: UUID?) : super(Companion.message.format(uuid))

    companion object {
        private const val message = "Person with id=%s not found."
    }
}
