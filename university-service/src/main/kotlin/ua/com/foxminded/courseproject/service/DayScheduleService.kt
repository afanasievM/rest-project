package ua.com.foxminded.courseproject.service

import java.time.LocalDate
import java.util.UUID
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.DayScheduleDto

interface DayScheduleService {
    fun getStudentDaysSchedule(startDay: LocalDate, endDay: LocalDate, id: UUID): Flux<Pair<LocalDate, DayScheduleDto?>>
    fun getTeacherDaysSchedule(startDay: LocalDate, endDay: LocalDate, id: UUID): Flux<Pair<LocalDate, DayScheduleDto?>>
    fun getStudentOneDaySchedule(date: LocalDate, id: UUID): Mono<Pair<LocalDate,DayScheduleDto?>>
    fun getTeacherOneDaySchedule(date: LocalDate, id: UUID): Mono<Pair<LocalDate,DayScheduleDto?>>
}

