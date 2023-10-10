package ua.com.foxminded.courseproject.repository

import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Subject

interface SubjectRepository : ReactiveCrudRepository<Subject, UUID> {
    override fun findById(id: UUID): Mono<Subject>

}
