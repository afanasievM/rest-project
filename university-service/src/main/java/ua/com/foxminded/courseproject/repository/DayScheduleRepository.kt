package ua.com.foxminded.courseproject.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import ua.com.foxminded.courseproject.entity.DaySchedule
import java.util.*

@Repository
interface DayScheduleRepository : CrudRepository<DaySchedule, UUID> {
    @Transactional
    @Query(
        value = "SELECT * FROM {h-schema}day_schedule WHERE day_number=(:number) AND id IN " +
                "(SELECT day_id FROM {h-schema}weeks_days WHERE week_id = " +
                "(SELECT id FROM {h-schema}weeks WHERE odd=FALSE))", nativeQuery = true
    )
    fun findDayScheduleByDayNumberFromOddWeek(@Param("number") number: Int): DaySchedule?

    @Transactional
    @Query(
        value = "SELECT * FROM {h-schema}day_schedule WHERE day_number=(:number) AND id IN " +
                "(SELECT day_id FROM {h-schema}weeks_days WHERE week_id = " +
                "(SELECT id FROM {h-schema}weeks WHERE odd=TRUE))", nativeQuery = true
    )
    fun findDayScheduleByDayNumberFromEvenWeek(@Param("number") number: Int): DaySchedule?
}