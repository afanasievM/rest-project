package ua.com.foxminded.courseproject.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import ua.com.foxminded.courseproject.entity.DaySchedule
import java.util.*

interface DayScheduleRepository : MongoRepository<DaySchedule, UUID> {
    @Query("{'odd':true}")
    fun findDayScheduleByDayNumberFromOddWeek(number: Int): DaySchedule?

    @Query("{'odd':false}")
    fun findDayScheduleByDayNumberFromEvenWeek(number: Int): DaySchedule?
}