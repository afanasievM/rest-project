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
        val expectedSize = 3

        Mockito.`when`(teacherService.findAll()).thenReturn(expected)

        webTestClient.get()
            .uri("/teachers")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(TeacherDto::class.java)
            .hasSize(expectedSize)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun createTeacher_shouldReturnCreated_WhenTeacherNotExists() {
        val teacher = setTeachers().blockFirst()
        teacher.firstName = "test1"
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", teacher.firstName)
        params.add("lastName", teacher.lastName)
        params.add("birthDay", teacher.birthDay!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("rank", teacher.rank)
        params.add("degree", teacher.degree)
        params.add("firstDay", teacher.firstDay!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("salary", teacher.salary.toString())
        params.add("title", teacher.title)

        Mockito.`when`(teacherService.save(teacher)).thenReturn(Mono.just(teacher))

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
        val teacher = setTeachers().blockFirst()
        teacher.firstName = "test1"
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", teacher.firstName)
        params.add("lastName", teacher.lastName)
        params.add("birthDay", teacher.birthDay!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("rank", teacher.rank)
        params.add("degree", teacher.degree)
        params.add("firstDay", teacher.firstDay!!.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("salary", teacher.salary.toString())
        params.add("title", teacher.title)

        Mockito.`when`(teacherService.findById(teacher!!.id!!)).thenReturn(Mono.just(teacher))
        Mockito.`when`(teacherService.save(teacher)).thenReturn(Mono.just(teacher))

        webTestClient.put()
            .uri("/teachers/{id}", teacher.id)
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

        Mockito.`when`(teacherService.delete(UUID.fromString(id))).thenReturn(Mono.empty<Void?>().then())

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