package ua.com.foxminded.courseproject.service

import org.springframework.data.domain.Pageable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.PersonDto
import java.util.*

interface PersonService<T : PersonDto> {
    fun findById(id: UUID): Mono<T>
    fun findAll(pageable: Pageable): Flux<T>
    fun save(dto: T): Mono<T>
    fun delete(id: UUID)
    fun personExists(personDto: T): Mono<Boolean>
}