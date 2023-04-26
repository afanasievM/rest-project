package ua.com.foxminded.courseproject.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.com.foxminded.courseproject.dto.*
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper
import ua.com.foxminded.courseproject.mapper.Mapper
import ua.com.foxminded.courseproject.repository.DayScheduleRepository
import java.time.LocalDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.*

@Service
open class DayScheduleServiceImpl @Autowired constructor(
    mapper: DayScheduleMapper, repository: DayScheduleRepository,
    teacherService: TeacherServiceImpl, studentService: StudentServiceImpl
) : DayScheduleService {
    private val mapper: Mapper<DayScheduleDto?, DaySchedule?>
    private val repository: DayScheduleRepository
    private val teacherService: TeacherServiceImpl
    private val studentService: StudentServiceImpl

    init {
        this.mapper = mapper
        this.repository = repository
        this.teacherService = teacherService
        this.studentService = studentService
    }

    override fun getStudentDaysSchedule(
        startDay: LocalDate,
        endDay: LocalDate,
        id: UUID
    ): Map<LocalDate, DayScheduleDto?> {
        return getDaysSchedule(startDay, endDay, studentService.findById(id))
    }

    override fun getTeacherDaysSchedule(
        startDay: LocalDate,
        endDay: LocalDate,
        id: UUID
    ): Map<LocalDate, DayScheduleDto?> {
        return getDaysSchedule(startDay, endDay, teacherService.findById(id))
    }

    override fun getStudentOneDaySchedule(date: LocalDate, id: UUID): DayScheduleDto? {
        return getPersonDaySchedule(date, studentService.findById(id))
    }

    override fun getTeacherOneDaySchedule(date: LocalDate, id: UUID): DayScheduleDto? {
        return getPersonDaySchedule(date, teacherService.findById(id))
    }

    private fun getDaysSchedule(
        startDay: LocalDate,
        endDay: LocalDate,
        personDto: PersonDto
    ): Map<LocalDate, DayScheduleDto?> {
        val result: MutableMap<LocalDate, DayScheduleDto?> = HashMap()
        val dayDifference = ChronoUnit.DAYS.between(startDay, endDay)
        for (i in 0..dayDifference) {
            val dayScheduleDto = getPersonDaySchedule(startDay.plusDays(i), personDto)
            result[startDay.plusDays(i)] = dayScheduleDto
        }
        return result
    }

    private fun getDaySchedule(date: LocalDate): DayScheduleDto? {
        val weekNumber = date[ChronoField.ALIGNED_WEEK_OF_YEAR]
        val dayNumber = date.dayOfWeek.value
        val daySchedule = if (weekNumber % 2 == 0) {
            repository.findDayScheduleByDayNumberFromOddWeek(dayNumber)
        } else {
            repository.findDayScheduleByDayNumberFromEvenWeek(dayNumber)
        }
        return mapper.toDto(daySchedule)
    }

    private fun getPersonDaySchedule(date: LocalDate, personDto: PersonDto): DayScheduleDto? {
        val dayScheduleDto = getDaySchedule(date)
        return if (personDto is StudentDto) {
            getStudentDaySchedule(dayScheduleDto, personDto)
        } else {
            getTeacherDaySchedule(dayScheduleDto, personDto as TeacherDto)
        }
    }

    private fun getStudentDaySchedule(dayScheduleDto: DayScheduleDto?, studentDto: StudentDto): DayScheduleDto? {
        dayScheduleDto?.lessons = filterStudentLessons(dayScheduleDto?.lessons as List<LessonDto>, studentDto)
        return dayScheduleDto
    }

    private fun getTeacherDaySchedule(dayScheduleDto: DayScheduleDto?, teacherDto: TeacherDto): DayScheduleDto? {
        dayScheduleDto?.lessons = filterTeacherLessons(dayScheduleDto?.lessons as List<LessonDto>, teacherDto)
        return dayScheduleDto
    }

    private fun filterStudentLessons(lessons: List<LessonDto>, studentDto: StudentDto): List<LessonDto> {
        return lessons
            .filter { l: LessonDto -> l.groups!!.contains(studentDto.group) }
            .toList()
    }

    private fun filterTeacherLessons(lessons: List<LessonDto>, teacherDto: TeacherDto): List<LessonDto> {
        return lessons.stream()
            .filter { l: LessonDto -> l.teacher == teacherDto }
            .toList()
    }
}