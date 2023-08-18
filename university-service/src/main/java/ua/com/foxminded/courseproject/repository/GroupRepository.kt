package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Group
import java.util.*

interface GroupRepository : ReactiveSortingRepository<Group, UUID> {
    override fun findById(id: UUID): Mono<Group>
    override fun findAllById(ids: MutableIterable<UUID>): Flux<Group>

}