package ua.com.foxminded.courseproject.repository

import java.util.UUID
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Group

interface GroupRepository : ReactiveCrudRepository<Group, UUID> {
    override fun findById(id: UUID): Mono<Group>
    fun findAllById(id: MutableCollection<UUID>): Flux<Group>

}
