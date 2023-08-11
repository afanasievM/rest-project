package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.ClassRoom
import ua.com.foxminded.courseproject.entity.Group
import java.util.*

interface ClassroomRepository : ReactiveSortingRepository<ClassRoom, UUID> {
    override fun findById(id: UUID): Mono<ClassRoom>

}