package ua.com.foxminded.courseproject.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.ScheduleDto
import java.util.*

interface ScheduleService {
    fun findById(id: UUID): Mono<ScheduleDto>
    fun findAll(): Flux<ScheduleDto>
}