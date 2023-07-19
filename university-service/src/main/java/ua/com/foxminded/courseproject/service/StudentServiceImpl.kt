package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.exceptions.StudentConflictException
import ua.com.foxminded.courseproject.exceptions.StudentNotFoundException
import ua.com.foxminded.courseproject.mapper.StudentMapper
import ua.com.foxminded.courseproject.repository.StudentRepository
import java.util.*

@Service
class StudentServiceImpl @Autowired constructor(
    private val mapper: StudentMapper,
    private val repository: StudentRepository
) : PersonService<StudentDto> {
    override fun findById(id: UUID): Mono<StudentDto> {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(StudentNotFoundException(id)))
            .map { mapper.toDto(it) }
    }

    override fun findAll(pageable: Pageable): Flux<StudentDto> {
        return repository.findAll().map { mapper.toDto(it) }
    }

    override fun save(student: StudentDto): Mono<StudentDto> {
        if (personExists(student).block() == true) {
            throw StudentConflictException(student)
        }
        return repository.save(mapper.toEntity(student)!!).map { mapper.toDto(it) }
    }

    override fun delete(id: UUID) {
        repository.findById(id).subscribe { repository.delete(it) }
    }

    override fun personExists(personDto: StudentDto): Mono<Boolean> {
        return repository.existsStudentByFirstNameAndLastNameAndBirthDay(
            personDto.firstName!!,
            personDto.lastName!!, personDto.birthDay!!
        )
    }
}
