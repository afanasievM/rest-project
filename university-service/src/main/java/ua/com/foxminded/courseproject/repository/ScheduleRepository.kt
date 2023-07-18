package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.Schedule
import java.util.*

interface ScheduleRepository : ReactiveSortingRepository<Schedule, UUID> {
    override fun findById(id: UUID): Mono<Schedule>
    override fun findAll(): Flux<Schedule>
}