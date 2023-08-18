package ua.com.foxminded.courseproject.repository

import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.DaySchedule

interface WeekScheduleRepository {

    fun findDayScheduleByDayNumberAndOddWeek(number: Int, odd: Boolean): Mono<DaySchedule?>

}

