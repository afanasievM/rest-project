package ua.com.foxminded.courseproject.integration

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import reactor.test.StepVerifier
import ua.com.foxminded.courseproject.config.DBTestConfig
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.service.StudentServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
@AutoConfigureWebTestClient
internal class StudentControllerIntegrationTest : DBTestConfig() {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Autowired
    private lateinit var studentService: StudentServiceImpl


    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun students_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() {
        val expectedSize = 2

        webTestClient.get()
            .uri("/students")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(StudentDto::class.java)
            .hasSize(expectedSize)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun createStudent_shouldReturnCreatedAndCreateStudent_WhenStudentNotExists() {
        val firstName = "firstName"
        val lastName = "LastName"
        val birthDay = LocalDate.now().minusYears(30)
        val courseNumber = "1"
        val captain = false
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", firstName)
        params.add("lastName", lastName)
        params.add("birthDay", birthDay.toString())
        params.add("course", courseNumber)
        params.add("captain", captain.toString())
        val expectedSize = 3

        webTestClient.post()
            .uri("/students")
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().isCreated

        StepVerifier.create(studentService.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateStudent_shouldReturnResetContentAndUpdateStudent_WhenStudentExists() {
        val existentStudent = studentService.findAll().blockFirst()
        val id = existentStudent.id.toString()
        val changedFirstname = "CHANGED"
        val existedLastName = existentStudent.lastName
        val courseNumber = "1"
        val existedBirthDay = existentStudent.birthDay?.minusYears(18)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", changedFirstname)
        params.add("lastName", existedLastName)
        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("course", courseNumber)

        webTestClient.put()
            .uri("/students/{id}", existentStudent.id)
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().is2xxSuccessful

        StepVerifier.create(studentService.findById(UUID.fromString(id)).log())
            .expectNextMatches { changedFirstname == it.firstName }
            .verifyComplete()
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun student_shouldReturnStudentAndOk_WhenStudentExists() {
        val existentStudent = studentService.findAll().blockFirst()
        val id = existentStudent.id.toString()

        webTestClient.get()
            .uri("/students/{id}", id)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun deleteStudent_shouldReturnNoContentAndDelete_WhenStudentExists() {
        val existentStudent = studentService.findAll().blockFirst()
        val id = existentStudent.id.toString()
        val expectedSize = 1

        webTestClient.delete()
            .uri("/students/{id}", id)
            .exchange()
            .expectStatus().isNoContent

        StepVerifier.create(studentService.findAll())
            .expectNextCount(expectedSize.toLong())
            .verifyComplete()
    }
}
