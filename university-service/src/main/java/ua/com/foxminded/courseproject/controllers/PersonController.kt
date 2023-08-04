package ua.com.foxminded.courseproject.controllers

import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.PersonDto
import ua.com.foxminded.courseproject.service.PersonService
import java.util.*

open class PersonController<T : PersonDto, S : PersonService<*>> {

    lateinit var service: S
    protected fun getPersons(pageable: Pageable): ResponseEntity<*> {
        return ResponseEntity<Any?>(service.findAll(pageable), HttpStatus.OK)
    }

    protected fun getPersonById(id: UUID): ResponseEntity<*> {
        val person = service.findById(id) as Mono<T>
        return ResponseEntity(person, HttpStatus.OK)
    }

    protected fun deletePersonById(id: UUID): ResponseEntity<*> {
        service.delete(id)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }
}
