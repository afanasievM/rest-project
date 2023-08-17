package ua.com.foxminded.courseproject.controllers

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
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
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.service.StudentServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


@SpringBootTest
@AutoConfigureWebTestClient
internal class StudentControllerTest {
    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var studentService: StudentServiceImpl


    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun students_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() {
        val expected = setStudents()
        val expectedSize = 3

        Mockito.`when`(studentService.findAll()).thenReturn(expected)

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
    fun createStudent_shouldReturnCreated_WhenStudentNotExists() {
        val student = setStudents().blockFirst()
        student.firstName = "test1"
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", student.firstName)
        params.add("lastName", student.lastName)
        params.add("birthDay", student.birthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("captain", student.captain.toString())
        params.add("course", student.course.toString())

        Mockito.`when`(studentService.save(any<StudentDto>())).thenReturn(Mono.just(student))

        webTestClient.post()
            .uri("/students")
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateStudent_shouldReturnResetContent_WhenStudentExists() {
        val existentStudent = setStudents().blockFirst()
        val id = existentStudent.id.toString()
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", existentStudent.firstName)
        params.add("lastName", existentStudent.lastName)
        params.add("birthDay", existentStudent.birthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("course", "2")
        params.add("captain", existentStudent.captain.toString())

        Mockito.`when`(studentService.findById(any())).thenReturn(Mono.just(existentStudent))
        Mockito.`when`(studentService.save(any<StudentDto>())).thenReturn(Mono.just(existentStudent))

        webTestClient
            .put()
            .uri("/students/{id}", id)
            .body(BodyInserters.fromMultipartData(params))
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun student_shouldReturnStudentAndOk_WhenStudentExists() {
        val existentStudent = setStudents().blockFirst()
        val id = existentStudent.id.toString()

        Mockito.`when`(studentService.findById(UUID.fromString(id))).thenReturn(Mono.just(existentStudent))

        webTestClient
            .get()
            .uri("/students/{id}", id)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(id)
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun deleteStudent_shouldReturnNoContent() {
        val id = UUID.randomUUID().toString()

        webTestClient
            .delete()
            .uri("/students/{id}", id)
            .exchange()
            .expectStatus().isNoContent
    }

    private fun setStudents(): Flux<StudentDto> {
        val studentDtos: MutableList<StudentDto> = ArrayList()
        for (i in 0..2) {
            val student = StudentDto()
            student.id = UUID.randomUUID()
            student.firstName = "firstName$i"
            student.lastName = "lastName$i"
            student.course = i
            student.birthDay = LocalDate.now()
            student.captain = false
            val group = GroupDto()
            group.id = UUID.randomUUID()
            group.name = "test"
            student.group = group
            studentDtos.add(student)
        }
        return Flux.fromIterable(studentDtos)
    }
}