package ua.com.foxminded.courseproject.integration


import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.service.TeacherServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
@AutoConfigureWebTestClient
internal class TeacherControllerIntegrationTest : DBTestConfig() {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var teacherService: TeacherServiceImpl

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() {
        val expectedSize = 2

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
    fun createTeacher_shouldReturnCreatedAndCreateTeacher_WhenTeacherNotExists() {
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
        params.add("firstDay", firstDay.toString())
        params.add("salary", salary.toString())
        params.add("title", title)
        val expectedSize = 3

        webTestClient.post()
            .uri("/teachers")
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().isCreated

        StepVerifier.create(teacherService.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateTeacher_shouldReturnResetContentAndUpdateTeacher_WhenTeacherExists() {
        val existentTeacher = teacherService.findAll().blockFirst()
        val id = existentTeacher.id.toString()
        val changedFirstname = "CHANGED"
        val existedLastName = existentTeacher.lastName
        val existedBirthDay = existentTeacher.birthDay?.minusYears(18)
        val existedFirstDay = existentTeacher.firstDay
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", changedFirstname)
        params.add("lastName", existedLastName)
        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("firstDay", existedFirstDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))


        webTestClient.put()
            .uri("/teachers/{id}", existentTeacher.id)
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().is2xxSuccessful

        StepVerifier.create(teacherService.findById(UUID.fromString(id)))
            .expectNextMatches { changedFirstname == it.firstName }
            .verifyComplete()
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacher_shouldReturnTeacherAndOk_WhenTeacherExists() {
        val existentTeacher = teacherService.findAll().blockFirst()
        val id = existentTeacher.id.toString()

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
    fun deleteTeacher_shouldReturnNoContentAndDelete_WhenTeacherExists() {
        val existentTeacher = teacherService.findAll().blockFirst()
        val id = existentTeacher.id.toString()
        val expectedSize = 1

        webTestClient.delete()
            .uri("/teachers/{id}", id)
            .exchange()
            .expectStatus().isNoContent

        StepVerifier.create(teacherService.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }
}
