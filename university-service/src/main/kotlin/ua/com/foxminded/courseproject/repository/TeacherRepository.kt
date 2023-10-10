package ua.com.foxminded.courseproject.repository

import java.time.LocalDate
import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Teacher

interface TeacherRepository : ReactiveCrudRepository<Teacher, UUID> {
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
