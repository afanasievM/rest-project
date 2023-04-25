package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.entity.Student
import ua.com.foxminded.courseproject.exceptions.StudentNotFoundException
import ua.com.foxminded.courseproject.mapper.StudentMapper
import ua.com.foxminded.courseproject.repository.StudentRepository
import java.util.*

@Service
open class StudentServiceImpl @Autowired constructor(
    private val mapper: StudentMapper,
    private val repository: StudentRepository
) : PersonService<StudentDto> {
    override fun findById(id: UUID): StudentDto {
        return mapper.toDto(repository.findById(id).orElseThrow { StudentNotFoundException(id) })!!
    }

    override fun findAll(pageable: Pageable): Page<StudentDto> {
        return repository.findAll(pageable).map { entity: Student -> mapper.toDto(entity) }
    }

    override fun save(student: StudentDto): StudentDto {
        return mapper.toDto(repository.save(mapper.toEntity(student)))!!
    }

    override fun delete(id: UUID) {
        repository.delete(repository.findById(id).get())
    }

    override fun personExists(personDto: StudentDto): Boolean {
        return repository.existsStudentByFirstNameAndLastNameAndBirthDay(
            personDto.firstName,
            personDto.lastName, personDto.birthDay
        )
    }
}
