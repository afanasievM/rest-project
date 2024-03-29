package ua.com.foxminded.courseproject.service

import java.time.LocalDate
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.bson.Document
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.dto.PersonDto
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.entity.DaySchedule
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper
import ua.com.foxminded.courseproject.mapper.Mapper
import ua.com.foxminded.courseproject.repository.WeekScheduleRepository

@Service
@Suppress("TooManyFunctions")
class DayScheduleServiceImpl(
    mapper: DayScheduleMapper, repository: WeekScheduleRepository,
    teacherService: TeacherServiceImpl, studentService: StudentServiceImpl
) : DayScheduleService {
    private val mapper: Mapper<DayScheduleDto?, DaySchedule?, Document>
    private val repository: WeekScheduleRepository
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
    ): Flux<Pair<LocalDate, DayScheduleDto?>> {
        return studentService.findById(id).flux().flatMap { getDaysSchedule(startDay, endDay, it) }

    }

    override fun getTeacherDaysSchedule(
        startDay: LocalDate,
        endDay: LocalDate,
        id: UUID
    ): Flux<Pair<LocalDate, DayScheduleDto?>> {
        return teacherService.findById(id).flux().flatMap { getDaysSchedule(startDay, endDay, it) }
    }

    override fun getStudentOneDaySchedule(date: LocalDate, id: UUID): Mono<Pair<LocalDate, DayScheduleDto?>> {
        return studentService.findById(id).flatMap { getPersonDaySchedule(date, it) }.map { Pair(date, it) }
    }

    override fun getTeacherOneDaySchedule(date: LocalDate, id: UUID): Mono<Pair<LocalDate, DayScheduleDto?>> {
        return teacherService.findById(id).flatMap { getPersonDaySchedule(date, it) }.map { Pair(date, it) }

    }

    private fun getDaysSchedule(
        startDay: LocalDate,
        endDay: LocalDate,
        personDto: PersonDto
    ): Flux<Pair<LocalDate, DayScheduleDto?>> {
        return Flux.range(0, ChronoUnit.DAYS.between(startDay, endDay).toInt() + 1)
            .map { startDay.plusDays(it.toLong()) }
            .flatMap {
                Mono.just(it)
                    .zipWith(getPersonDaySchedule(it, personDto))
                    .map { Pair(it.t1, it.t2) }
            }
    }

    private fun getDaySchedule(date: LocalDate): Mono<DayScheduleDto?> {
        val weekNumber = date[ChronoField.ALIGNED_WEEK_OF_YEAR]
        val dayNumber = date.dayOfWeek.value
        val odd = weekNumber % 2 == 0
        val daySchedule = repository.findDayScheduleByDayNumberAndOddWeek(dayNumber, odd)
        return daySchedule.map { mapper.toDto(it) }
    }

    private fun getPersonDaySchedule(date: LocalDate, personDto: PersonDto): Mono<DayScheduleDto?> {
        return getDaySchedule(date).map {
            return@map if (personDto is StudentDto) {
                getStudentDaySchedule(it, personDto)
            } else {
                getTeacherDaySchedule(it, personDto as TeacherDto)
            }
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
