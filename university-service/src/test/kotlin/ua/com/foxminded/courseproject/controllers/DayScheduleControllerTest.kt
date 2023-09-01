package ua.com.foxminded.courseproject.controllers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.DayScheduleDto
import ua.com.foxminded.courseproject.dto.LessonDto
import ua.com.foxminded.courseproject.service.DayScheduleService
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureWebTestClient
internal class DayScheduleControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var service: DayScheduleService
    private lateinit var daySchedulePairs: Flux<Pair<LocalDate, DayScheduleDto?>>

    @BeforeEach
    fun setUp() {
        setDaySchedule()
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
        val startDate = LocalDate.now()
        val daySchedule = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = UUID.randomUUID().toString()
        params["startdate"] = listOf(startDate.toString())

        Mockito.`when`(service.getTeacherOneDaySchedule(any<LocalDate>(), any<UUID>()))
            .thenReturn(Mono.just(Pair(startDate, daySchedule)))

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
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(1)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = UUID.randomUUID().toString()
        val dayScheduleStart = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val dayScheduleEnd = daySchedulePairs.filter { it.first == endDate }.blockFirst()?.second
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        Mockito.`when`(service.getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId)))
            .thenReturn(daySchedulePairs)

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
            .jsonPath("$.[0].second.id").isEqualTo(dayScheduleStart?.id.toString())
            .jsonPath("$.[1].second.id").isEqualTo(dayScheduleEnd?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacherSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.now()
        val endDate = startDate.minusDays(1)
        val daySchedule = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = UUID.randomUUID().toString()
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        Mockito.`when`(service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId)))
            .thenReturn(Mono.just(Pair(startDate, daySchedule)))

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
        val startDate = LocalDate.now()
        val daySchedule = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val teacherId = UUID.randomUUID().toString()
        params["startdate"] = listOf(startDate.toString())

        Mockito.`when`(service.getStudentOneDaySchedule(startDate, UUID.fromString(teacherId)))
            .thenReturn(Mono.just(Pair(startDate, daySchedule)))

        webTestClient
            .get()
            .uri { it
                .path("/students/{id}/schedule")
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
    fun studentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.now()
        val endDate = startDate.plusDays(1)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val studentId = UUID.randomUUID().toString()
        val dayScheduleStart = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val dayScheduleEnd = daySchedulePairs.filter { it.first == endDate }.blockFirst()?.second
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        Mockito.`when`(service.getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId)))
            .thenReturn(daySchedulePairs)

        webTestClient
            .get()
            .uri { it
                .path("/students/{id}/schedule")
                .queryParams(params)
                .build(studentId)
            }
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.[0].second.id").isEqualTo(dayScheduleStart?.id.toString())
            .jsonPath("$.[1].second.id").isEqualTo(dayScheduleEnd?.id.toString())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun studentSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
        val startDate = LocalDate.now()
        val endDate = startDate.minusDays(1)
        val daySchedule = daySchedulePairs.filter { it.first == startDate }.blockFirst()?.second
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val studentId = UUID.randomUUID().toString()
        params["startdate"] = listOf(startDate.toString())
        params["enddate"] = listOf(endDate.toString())

        Mockito.`when`(service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId)))
            .thenReturn(Mono.just(Pair(startDate, daySchedule)))

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

    private fun setDaySchedule() {
        val lessonOne = LessonDto()
        lessonOne.id = UUID.randomUUID()
        val lessonTwo = LessonDto()
        lessonTwo.id = UUID.randomUUID()
        val firstDay = DayScheduleDto()
        firstDay.id = UUID.randomUUID()
        firstDay.dayNumber = 1
        firstDay.lessons = Arrays.asList(lessonOne, lessonTwo)
        val secondDay = DayScheduleDto()
        secondDay.id = UUID.randomUUID()
        secondDay.dayNumber = 2
        secondDay.lessons = Arrays.asList(lessonOne)
        daySchedulePairs = Flux.just(Pair(LocalDate.now(), firstDay), Pair(LocalDate.now().plusDays(1), secondDay))
    }
}