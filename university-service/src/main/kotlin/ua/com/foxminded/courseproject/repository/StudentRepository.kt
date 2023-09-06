package ua.com.foxminded.courseproject.repository

import java.time.LocalDate
import java.util.UUID
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Student

interface StudentRepository {
    fun findById(id: UUID): Mono<Student>
    fun findAll(): Flux<Student>

    fun save(student: Student): Mono<Student>
    fun delete(student: Student): Mono<Void>
    fun existsStudentByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Mono<Boolean>
}

