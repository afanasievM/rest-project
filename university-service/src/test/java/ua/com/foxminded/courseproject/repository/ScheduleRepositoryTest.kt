//package ua.com.foxminded.courseproject.repository
//
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
//import org.springframework.data.util.Streamable
//import ua.com.foxminded.courseproject.config.DBTestConfig
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import java.util.*
//
//@DataMongoTest
//internal open class ScheduleRepositoryTest : DBTestConfig() {
//    @Autowired
//    private lateinit var repository: ScheduleRepository
//
//    @Test
//    fun findAll_shouldReturnListDays() {
//        val expectedSize = 1
//
//        val schedules = Streamable.of(repository.findAll()).toList()
//
//        Assertions.assertEquals(expectedSize, schedules.size)
//    }
//
//    @Test
//    fun findById_shouldReturnDay_whenIdExists() {
//        val id = UUID.fromString("b5fd8224-47ba-11ed-b878-0242ac120002")
//        val expectedTime = LocalDateTime.parse("2022-09-01T08:00:00.000Z", DateTimeFormatter.ISO_DATE_TIME)
//
//        val actual = repository.findById(id).get()
//
//        Assertions.assertEquals(expectedTime, actual.startDate)
//    }
//}