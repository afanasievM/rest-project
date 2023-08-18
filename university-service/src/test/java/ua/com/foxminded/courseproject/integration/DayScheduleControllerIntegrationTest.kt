package ua.com.foxminded.courseproject.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.service.DayScheduleService
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureWebTestClient
internal class DayScheduleControllerIntegrationTest : DBTestConfig() {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var service: DayScheduleService

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
        val startDate = LocalDate.of(2022, 10, 14)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
        val daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId)).block()?.second
        params["startdate"] = listOf(startDate.toString())

        webTestClient
            .get()
            .uri {
                it.path("/teachers/{id}/schedule")
                    .queryParams(params)
                    .build(teacherId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[0].second.id").isEqualTo(daySchedule?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacherSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.of(2022, 10, 14)
        val endDate = startDate.plusDays(1)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
        val dayScheduleStart = service
            .getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId))
            .filter { it.first == startDate }.blockFirst()?.second
        val dayScheduleEnd = service
            .getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId))
            .filter { it.first == endDate }.blockFirst()?.second
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        webTestClient
            .get()
            .uri {
                it.path("/teachers/{id}/schedule")
                    .queryParams(params)
                    .build(teacherId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[1].second.id").isEqualTo(dayScheduleStart?.id.toString())
            .jsonPath("$.[0].second.id").isEqualTo(dayScheduleEnd?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.now()
        val endDate = startDate.minusDays(1)
        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
        val daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId)).block()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        webTestClient
            .get()
            .uri {
                it.path("/teachers/{id}/schedule")
                    .queryParams(params)
                    .build(teacherId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[0].second.id").isEqualTo(daySchedule?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun studentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
        val startDate = LocalDate.of(2022, 10, 14)
        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
        val daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId)).block()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["startdate"] = listOf(startDate.toString())

        webTestClient
            .get()
            .uri {
                it
                    .path("/students/{id}/schedule")
                    .queryParams(params)
                    .build(studentId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[0].second.id").isEqualTo(daySchedule?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun studentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.of(2022, 10, 14)
        val endDate = startDate.plusDays(1)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
        val dayScheduleList =
            service.getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId)).collectList().block()
        val dayScheduleStart = dayScheduleList.filter { it.first == startDate }.first().second
        val dayScheduleEnd = dayScheduleList.filter { it.first == endDate }.first().second
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        webTestClient
            .get()
            .uri {
                it
                    .path("/students/{id}/schedule")
                    .queryParams(params)
                    .build(studentId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[1].second.id").isEqualTo(dayScheduleStart?.id.toString())
            .jsonPath("$.[0].second.id").isEqualTo(dayScheduleEnd?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun studentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.now()
        val endDate = startDate.minusDays(1)
        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
        val daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId)).block()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        webTestClient
            .get()
            .uri {
                it
                    .path("/students/{id}/schedule")
                    .queryParams(params)
                    .build(studentId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody().jsonPath("$.[0].second.id").isEqualTo(daySchedule?.id.toString())
    }
}