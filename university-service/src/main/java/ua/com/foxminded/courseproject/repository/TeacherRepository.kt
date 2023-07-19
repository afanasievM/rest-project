package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*

interface TeacherRepository : ReactiveSortingRepository<Teacher, UUID> {
    override fun findAll(): Flux<Teacher>
    override fun findById(id: UUID): Mono<Teacher>
    fun save(teacher: Teacher): Mono<Teacher>
    override fun delete(teacher: Teacher): Mono<Void>
    fun existsTeacherByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Mono<Boolean>
}