package ua.com.foxminded.courseproject.repository

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.util.Streamable
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import java.sql.Timestamp
import java.util.*

@SpringBootTest
@Sql(value = ["classpath:initial_data.sql"])
@Transactional
internal open class ScheduleRepositoryTest {
    @Autowired
    private lateinit var repository: ScheduleRepository

    @Test
    fun findAll_shouldReturnListDays() {
        val expectedSize = 1

        val schedules = Streamable.of(repository.findAll()).toList()

        Assertions.assertEquals(expectedSize, schedules.size)
    }

    @Test
    fun findById_shouldReturnDay_whenIdExists() {
        val id = UUID.fromString("b5fd8224-47ba-11ed-b878-0242ac120002")
        val expectedTime = Timestamp.valueOf("2022-09-01 08:00:00")

        val actual = repository.findById(id).get()

        Assertions.assertEquals(expectedTime, actual.startDate)
    }
}