package ua.com.foxminded.courseproject.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ua.com.foxminded.courseproject.entity.Schedule
import java.util.*

interface ScheduleRepository : MongoRepository<Schedule, UUID> {
    override fun findById(id: UUID): Optional<Schedule>
    override fun findAll(): List<Schedule>
}