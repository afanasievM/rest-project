package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.data.util.Streamable
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.entity.DaySchedule
import java.util.*

@DataMongoTest
internal open class WeekScheduleRepositoryTest : DBTestConfig() {
    @Autowired
    private lateinit var repository: WeekScheduleRepository

    @Test
    fun findAll_shouldReturnListWeeks() {
        val expectedSize = 2

        val days = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, days.size)
    }

    @Test
    fun findById_shouldReturnEmptyOptional_whenIdNotExists() {
        val id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002")

        val actual = repository.findById(id)

        Assertions.assertEquals(true, actual.isEmpty)
    }

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnDay_whenNumberExistsAndWeekOdd() {
        val idExpected = UUID.fromString("9496a616-4633-11ed-b878-0242ac120002")
        val expected = mongoTemplate.findById(idExpected, DaySchedule::class.java)
        val number = 1

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number)

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnDay_whenNumberExistsAndWeekEven() {
        val idExpected = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002")
        val expected = mongoTemplate.findById(idExpected, DaySchedule::class.java)
        val number = 1

        val actual = repository.findDayScheduleByDayNumberFromEvenWeek(number)


        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekOdd() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number)

        Assertions.assertEquals(null, actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekEven() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number)

        Assertions.assertEquals(null, actual)
    }

}