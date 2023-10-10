package ua.com.foxminded.courseproject.repository

import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.ClassRoom

interface ClassroomRepository : ReactiveCrudRepository<ClassRoom, UUID> {
    override fun findById(id: UUID): Mono<ClassRoom>

}
