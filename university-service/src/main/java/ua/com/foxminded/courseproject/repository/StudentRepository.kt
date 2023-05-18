package ua.com.foxminded.courseproject.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import ua.com.foxminded.courseproject.entity.Student
import java.time.LocalDate
import java.util.*

interface StudentRepository : PagingAndSortingRepository<Student, UUID> {
    override fun findById(id: UUID): Optional<Student>
    override fun findAll(pageable: Pageable): Page<Student>
    fun save(student: Student): Student
    override fun delete(student: Student)
    fun existsStudentByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Boolean
}