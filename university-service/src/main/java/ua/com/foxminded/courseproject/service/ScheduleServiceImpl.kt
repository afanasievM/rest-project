package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.com.foxminded.courseproject.dto.ScheduleDto
import ua.com.foxminded.courseproject.entity.Schedule
import ua.com.foxminded.courseproject.mapper.ScheduleMapper
import ua.com.foxminded.courseproject.repository.ScheduleRepository
import java.util.*

@Service
open class ScheduleServiceImpl @Autowired constructor(
    private val mapper: ScheduleMapper,
    private val repository: ScheduleRepository
) : ScheduleService {
    override fun findById(id: UUID): ScheduleDto? {
        return mapper.toDto(repository.findById(id).get())
    }

    override fun findAll(): List<ScheduleDto?> {
        return repository.findAll().map { entity: Schedule -> mapper.toDto(entity) }
            .toList()
    }
}