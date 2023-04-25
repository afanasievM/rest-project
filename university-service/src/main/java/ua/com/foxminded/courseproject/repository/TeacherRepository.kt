package ua.com.foxminded.courseproject.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ua.com.foxminded.courseproject.entity.Teacher
import java.time.LocalDate
import java.util.*

@Repository
interface TeacherRepository : CrudRepository<Teacher, UUID> {
    fun findAll(pageable: Pageable?): Page<Teacher>
    override fun findById(id: UUID): Optional<Teacher>
    fun save(teacher: Teacher): Teacher
    override fun delete(teacher: Teacher)
    fun existsTeacherByFirstNameAndLastNameAndBirthDay(
        firstName: String,
        lastName: String,
        birthDay: LocalDate
    ): Boolean
}