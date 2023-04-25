package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.util.Streamable
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@Sql(value = ["classpath:initial_data.sql"])
@Transactional
internal open class DayScheduleRepositoryTest {
    @Autowired
    private lateinit var repository: DayScheduleRepository

    @Test
    fun findAll_shouldReturnListDays() {
        val expectedSize = 14

        val days = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, days.size)
    }

    @Test
    fun findById_shouldReturnDay_whenIdExists() {
        val id = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002")
        val expectedNumber = 1

        val actual = repository.findById(id).get()

        Assertions.assertEquals(expectedNumber, actual.dayNumber)
    }

    @Test
    fun findById_shouldReturnEmptyOptional_whenIdNotExists() {
        val id = UUID.fromString("e966f7c0-4621-11ed-b838-0242ac120002")

        val actual = repository.findById(id)

        Assertions.assertEquals(true, actual.isEmpty)
    }

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnDay_whenNumberExistsAndWeekOdd() {
        val idExpected = UUID.fromString("949694f0-4633-11ed-b878-0242ac120002")
        val expected = repository.findById(idExpected).get()
        val number = 1

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number).get()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnDay_whenNumberExistsAndWeekEven() {
        val idExpected = UUID.fromString("9496a616-4633-11ed-b878-0242ac120002")
        val expected = repository.findById(idExpected).get()
        val number = 1

        val actual = repository.findDayScheduleByDayNumberFromEvenWeek(number).get()

        Assertions.assertEquals(expected, actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromOddWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekOdd() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number)

        Assertions.assertEquals(Optional.empty<Any>(), actual)
    }

    @Test
    fun findDayScheduleByDayNumberFromEvenWeek_shouldReturnEmptyOptional_whenNumberNotExistsAndWeekEven() {
        val number = 10

        val actual = repository.findDayScheduleByDayNumberFromOddWeek(number)

        Assertions.assertEquals(Optional.empty<Any>(), actual)
    }
}