package ua.com.foxminded.courseproject.repository

import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Subject

interface SubjectRepository : ReactiveSortingRepository<Subject, UUID> {
    override fun findById(id: UUID): Mono<Subject>

}
