package ua.com.foxminded.courseproject.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ua.com.foxminded.courseproject.dto.PersonDto
import java.util.*

interface PersonService<T : PersonDto> {
    fun findById(id: UUID): T
    fun findAll(pageable: Pageable): Page<T>
    fun save(dto: T): T
    fun delete(id: UUID)
    fun personExists(personDto: T): Boolean
}