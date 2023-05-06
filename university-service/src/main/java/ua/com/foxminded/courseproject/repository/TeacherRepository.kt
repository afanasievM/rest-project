package ua.com.foxminded.courseproject.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*

interface TeacherRepository : PagingAndSortingRepository<Teacher, UUID> {
    override fun findAll(pageable: Pageable?): Page<Teacher>
    override fun findById(id: UUID): Optional<Teacher>
    fun save(teacher: Teacher): Teacher
    override fun delete(teacher: Teacher)
    fun existsTeacherByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Boolean
}