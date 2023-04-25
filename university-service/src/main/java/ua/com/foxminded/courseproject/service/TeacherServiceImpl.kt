package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.entity.Teacher
import ua.com.foxminded.courseproject.exceptions.TeacherConflictException
import ua.com.foxminded.courseproject.exceptions.TeacherNotFoundException
import ua.com.foxminded.courseproject.mapper.TeacherMapper
import ua.com.foxminded.courseproject.repository.TeacherRepository
import java.util.*

@Service
open class TeacherServiceImpl @Autowired constructor(
    private val mapper: TeacherMapper,
    private val repository: TeacherRepository
) : PersonService<TeacherDto> {
    override fun findById(id: UUID): TeacherDto {
        return mapper.toDto(repository.findById(id).orElseThrow { TeacherNotFoundException(id) })
    }

    override fun findAll(pageable: Pageable): Page<TeacherDto> {
        return repository.findAll(pageable).map { entity: Teacher? -> mapper.toDto(entity) }
    }

    override fun save(teacher: TeacherDto): TeacherDto {
        if (personExists(teacher)) {
            throw TeacherConflictException(teacher)
        }
        return mapper.toDto(repository.save(mapper.toEntity(teacher)))
    }

    override fun delete(id: UUID) {
        repository.delete(repository.findById(id).get())
    }

    override fun personExists(personDto: TeacherDto): Boolean {
        return repository.existsTeacherByFirstNameAndLastNameAndBirthDay(
            personDto.firstName,
            personDto.lastName, personDto.birthDay
        )
    }
}