//package ua.com.foxminded.courseproject.repository
//
//import org.springframework.data.repository.reactive.ReactiveSortingRepository
//import org.springframework.stereotype.Component
//import reactor.core.publisher.Flux
//import reactor.core.publisher.Mono
//import ua.com.foxminded.courseproject.entity.Student
//import java.time.LocalDate
//import java.util.*
//
//@Component
//interface StudentRepository : ReactiveSortingRepository<Student, UUID> {
//    override fun findById(id: UUID): Mono<Student>
//    override fun findAll(): Flux<Student>
//
//    fun save(student: Student): Mono<Student>
//    override fun delete(student: Student): Mono<Void>
//    fun existsStudentByFirstNameAndLastNameAndBirthDay(
//        firstName: String,
//        lastName: String,
//        birthDay: LocalDate
//    ): Mono<Boolean>
//}