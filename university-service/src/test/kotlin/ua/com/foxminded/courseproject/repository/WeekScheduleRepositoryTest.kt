package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.mapper.ClassRoomMapper
import ua.com.foxminded.courseproject.mapper.DayScheduleMapper
import ua.com.foxminded.courseproject.mapper.GroupMapper
import ua.com.foxminded.courseproject.mapper.LessonMapper
import ua.com.foxminded.courseproject.mapper.StudentMapper
import ua.com.foxminded.courseproject.mapper.SubjectMapper
import ua.com.foxminded.courseproject.mapper.TeacherMapper
import java.util.*

@DataMongoTest
@Import(
    WeekScheduleRepositoryImp::class, StudentMapper::class, GroupMapper::class, DayScheduleMapper::class,
    LessonMapper::class, ClassRoomMapper::class, SubjectMapper::class, TeacherMapper::class,
    LessonRepositoryImp::class
)
internal open class WeekScheduleRepositoryTest : DBTestConfig() {
    @Autowired
    private lateinit var repository: WeekScheduleRepository

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnDay_whenNumberExistsAndWeekOdd() {
        val idExpected = UUID.fromString("9496a616-4633-11ed-b878-0242ac120002")
        val number = 1

        val actual = repository.findDayScheduleByDayNumberAndOddWeek(number, true)

        StepVerifier.create(actual)
            .expectNextMatches { it!!.id!!.equals(idExpected) }
            .verifyComplete()
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnDay_whenNumberExistsAndWeekEven() {
        val idExpected = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002")
        val number = 1

        val actual = repository.findDayScheduleByDayNumberAndOddWeek(number, false)

        StepVerifier.create(actual)
            .expectNextMatches { it!!.id!!.equals(idExpected) }
            .verifyComplete()
    }

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekOdd() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberAndOddWeek(number, true)

        StepVerifier.create(actual)
            .expectNextCount(0)
            .verifyComplete()
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekEven() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberAndOddWeek(number, false)

        StepVerifier.create(actual)
            .expectNextCount(0)
            .verifyComplete()
    }

}
