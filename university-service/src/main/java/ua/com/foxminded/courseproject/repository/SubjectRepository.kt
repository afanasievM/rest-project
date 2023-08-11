package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Group
import ua.com.foxminded.courseproject.entity.Subject
import java.util.*

interface SubjectRepository : ReactiveSortingRepository<Subject, UUID> {
    override fun findById(id: UUID): Mono<Subject>

}