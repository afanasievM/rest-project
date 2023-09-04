package ua.com.foxminded.courseproject.service

import java.util.UUID
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.PersonDto

interface PersonService<T : PersonDto> {
    fun findById(id: UUID): Mono<T>
    fun findAll(): Flux<T>
    fun save(dto: T): Mono<T>
    fun delete(id: UUID): Mono<Void>
    fun personExists(personDto: T): Mono<Boolean>
}
