package ua.com.foxminded.courseproject.controllers

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.service.TeacherServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
@AutoConfigureWebTestClient
internal class TeacherControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var teacherService: TeacherServiceImpl


    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() {
        val expected = setTeachers()

        Mockito.`when`(teacherService.findAll()).thenReturn(expected)

        webTestClient.get()
            .uri("/teachers")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(TeacherDto::class.java)
            .hasSize(3)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun createTeacher_shouldReturnCreated_WhenTeacherNotExists() {
        val firstName = "test1"
        val lastName = "test2"
        val birthDay = LocalDate.now().minusYears(18)
        val degree = "NTUU KPI"
        val firstDay = LocalDate.now()
        val rank = "phd"
        val salary = 1000
        val title = "teacher"
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", firstName)
        params.add("lastName", lastName)
        params.add("birthDay", birthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("rank", rank)
        params.add("degree", degree)
        params.add("firstDay", firstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("salary", salary.toString())
        params.add("title", title)

        Mockito.`when`(teacherService.findAll()).thenReturn(setTeachers())

        webTestClient.post()
            .uri("/teachers")
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateTeacher_shouldReturnResetContent_WhenTeacherExists() {
        val existentTeacher = setTeachers().blockFirst()
        val id = existentTeacher.id.toString()
        val existedFirstName = existentTeacher.firstName
        val existedLastName = existentTeacher.lastName
        val existedBirthDay = existentTeacher.birthDay?.minusYears(18)
        val existedFirstDay = existentTeacher.firstDay
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", existedFirstName)
        params.add("lastName", existedLastName)
        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("firstDay", existedFirstDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))

        Mockito.`when`(teacherService.findById(UUID.fromString(id))).thenReturn(Mono.just(existentTeacher))

        webTestClient.put()
            .uri("/teachers/{id}", id)
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacher_shouldReturnTeacherAndOk_WhenTeacherExists() {
        val existentTeacher = setTeachers().blockFirst()
        val id = existentTeacher.id.toString()

        Mockito.`when`(teacherService.findById(UUID.fromString(id))).thenReturn(Mono.just(existentTeacher))

        webTestClient.get()
            .uri("/teachers/{id}", id)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun deleteTeacher_shouldReturnNoContent() {
        val id = UUID.randomUUID().toString()

        webTestClient.delete()
            .uri("/teachers/{id}", id)
            .exchange()
            .expectStatus().isNoContent
    }

    private fun setTeachers(): Flux<TeacherDto> {
        val teacherDtos: MutableList<TeacherDto> = ArrayList()
        for (i in 0..2) {
            val teacher = TeacherDto()
            teacher.id = UUID.randomUUID()
            teacher.firstName = "firstName$i"
            teacher.lastName = "lastName$i"
            teacher.birthDay = LocalDate.now()
            teacher.rank = "phd"
            teacher.salary = i
            teacher.title = "teacher"
            teacher.firstDay = LocalDate.now().minusDays(1000)
            teacher.degree = "NTUU KPI"
            teacherDtos.add(teacher)
        }
        return Flux.fromIterable(teacherDtos)
    }
}