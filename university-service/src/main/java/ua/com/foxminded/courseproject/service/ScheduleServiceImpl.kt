package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.ScheduleDto
import ua.com.foxminded.courseproject.entity.Schedule
import ua.com.foxminded.courseproject.mapper.ScheduleMapper
import ua.com.foxminded.courseproject.repository.ScheduleRepository
import java.util.*

@Service
class ScheduleServiceImpl @Autowired constructor(
    private val mapper: ScheduleMapper,
    private val repository: ScheduleRepository
) : ScheduleService {
    override fun findById(id: UUID): Mono<ScheduleDto> {
        return repository.findById(id).map { mapper.toDto(it) }
    }

    override fun findAll(): Flux<ScheduleDto> {
        return repository.findAll().map { mapper.toDto(it) }
    }
}