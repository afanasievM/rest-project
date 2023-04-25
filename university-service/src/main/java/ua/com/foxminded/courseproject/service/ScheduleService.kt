package ua.com.foxminded.courseproject.service

import ua.com.foxminded.courseproject.dto.ScheduleDto
import java.util.*

interface ScheduleService {
    fun findById(id: UUID): ScheduleDto
    fun findAll(): List<ScheduleDto>
}