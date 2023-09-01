package ua.com.foxminded.courseproject.entity

import java.time.LocalDate
import java.util.*


abstract class Person {
    abstract var id: UUID?
    abstract var firstName: String?
    abstract var lastName: String?
    abstract var birthDay: LocalDate?
}


