package ua.com.foxminded.courseproject.repository

import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.entity.DaySchedule

interface WeekScheduleRepository{

    fun findDayScheduleByDayNumberFromOddWeek(number: Int): Mono<DaySchedule?>

    fun findDayScheduleByDayNumberFromEvenWeek(number: Int): Mono<DaySchedule?>

}

