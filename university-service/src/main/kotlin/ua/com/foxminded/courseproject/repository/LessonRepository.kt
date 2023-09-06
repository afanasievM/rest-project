package ua.com.foxminded.courseproject.repository

import java.util.UUID
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Lesson

interface LessonRepository {
    fun findById(id: UUID): Mono<Lesson>
    fun findAllById(ids: MutableIterable<UUID>): Flux<Lesson>

}
