package ua.com.foxminded.courseproject.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ua.com.foxminded.courseproject.entity.Schedule
import java.util.*

@Repository
interface ScheduleRepository : CrudRepository<Schedule, UUID> {
    override fun findById(id: UUID): Optional<Schedule>
    override fun findAll(): List<Schedule>
}