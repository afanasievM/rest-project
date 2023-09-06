package ua.com.foxminded.courseproject.controllers

import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.PersonDto
import ua.com.foxminded.courseproject.service.PersonService

open class PersonController<T : PersonDto, S : PersonService<*>> {

    lateinit var service: S
    protected fun getPersons(): ResponseEntity<*> {
        return ResponseEntity<Any?>(service.findAll(), HttpStatus.OK)
    }

    protected fun getPersonById(id: UUID): ResponseEntity<*> {
        val person = service.findById(id) as Mono<T>
        return ResponseEntity(person, HttpStatus.OK)
    }

    protected fun deletePersonById(id: UUID): Mono<ResponseEntity<*>> {
        return service.delete(id).thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build<Void>())
    }
}
