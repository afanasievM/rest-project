package ua.com.foxminded.courseproject.service

import ua.com.foxminded.courseproject.dto.DayScheduleDto
import java.time.LocalDate
import java.util.*

interface DayScheduleService {
    fun getStudentDaysSchedule(startDay: LocalDate, endDay: LocalDate, id: UUID): Map<LocalDate, DayScheduleDto?>
    fun getTeacherDaysSchedule(startDay: LocalDate, endDay: LocalDate, id: UUID): Map<LocalDate, DayScheduleDto?>
    fun getStudentOneDaySchedule(date: LocalDate, id: UUID): DayScheduleDto?
    fun getTeacherOneDaySchedule(date: LocalDate, id: UUID): DayScheduleDto?
}