package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.exceptions.TeacherConflictException
import ua.com.foxminded.courseproject.exceptions.TeacherNotFoundException
import ua.com.foxminded.courseproject.mapper.TeacherMapper
import ua.com.foxminded.courseproject.repository.TeacherRepository
import java.util.*

@Service
class TeacherServiceImpl @Autowired constructor(
    private val mapper: TeacherMapper,
    private val repository: TeacherRepository
) : PersonService<TeacherDto> {
    override fun findById(id: UUID): Mono<TeacherDto> {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(TeacherNotFoundException(id)))
            .map { mapper.toDto(it) }
    }

    override fun findAll(): Flux<TeacherDto> {
        return repository.findAll().map { mapper.toDto(it) }
    }

    override fun save(teacher: TeacherDto): Mono<TeacherDto> {
        return personExists(teacher).flatMap {
            if (it == true) {
                Mono.error(TeacherConflictException(teacher))
            } else {
                repository.save(mapper.toEntity(teacher)).map { mapper.toDto(it) }
            }
        }
    }

    override fun delete(id: UUID) {
        repository.findById(id).subscribe { repository.delete(it) }
    }

    override fun personExists(personDto: TeacherDto): Mono<Boolean> {
        return repository.existsTeacherByFirstNameAndLastNameAndBirthDay(
            personDto.firstName!!,
            personDto.lastName!!, personDto.birthDay!!
        )
    }
}