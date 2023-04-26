package ua.com.foxminded.courseproject.controllers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ua.com.foxminded.courseproject.dto.GroupDto
import ua.com.foxminded.courseproject.dto.StudentDto
import ua.com.foxminded.courseproject.service.StudentServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var studentService: StudentServiceImpl
    private lateinit var pageableDefault: Pageable
    private var pageDefault: Int = 0
    private var sizeDefault: Int = 0

    @BeforeEach
    fun setUp() {
        pageDefault = 0
        sizeDefault = 5
        pageableDefault = PageRequest.of(pageDefault, sizeDefault)
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun students_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() {
        val expected = setStudents(pageableDefault)

        Mockito.`when`(studentService.findAll(pageableDefault)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expected.totalElements))
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun students_shouldReturnStudentsPageAndOk_WhenRequestWithCorrectParameters() {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val expected = setStudents(pageableDefault)
        params["size"] = listOf(sizeDefault.toString())
        params["page"] = listOf(pageDefault.toString())

        Mockito.`when`(studentService.findAll(pageableDefault)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/students").params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expected.content[0].id.toString()))
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun students_shouldReturnPageWithAllStudentsAndOk_WhenPageSizeMoreStudentsNumber() {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val pageNumber = "0"
        val size = "10"
        val pageable: Pageable = PageRequest.of(pageNumber.toInt(), size.toInt())
        val expected = setStudents(pageable)
        params["size"] = listOf(size)
        params["page"] = listOf(pageNumber)

        Mockito.`when`(studentService.findAll(pageable)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/students").params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expected.totalElements))
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun createStudent_shouldReturnCreated_WhenStudentNotExists() {
        val firstName = "test1"
        val lastName = setStudents(pageableDefault).content[0].lastName
        val birthDay = setStudents(pageableDefault).content[0].birthDay?.minusYears(18)
        val courseNumber = "1"
        val captain = false
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", firstName)
        params.add("lastName", lastName)
        params.add("birthDay", birthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("captain", captain.toString())
        params.add("course", courseNumber)
        val studentDto = StudentDto()
        studentDto.firstName = firstName
        studentDto.lastName = lastName
        studentDto.birthDay = birthDay
        studentDto.course = Integer.valueOf(courseNumber)
        studentDto.captain = captain

        Mockito.`when`(studentService.personExists(studentDto)).thenReturn(false)

        mockMvc.perform(MockMvcRequestBuilders.post("/students").queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateStudent_shouldReturnResetContent_WhenStudentExists() {
        val existentStudent = setStudents(pageableDefault).content[0]
        val id = existentStudent.id.toString()
        val existedFirstName = existentStudent.firstName
        val existedLastName = existentStudent.lastName
        val courseNumber = "1"
        val existedBirthDay = existentStudent.birthDay?.minusYears(18)
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", existedFirstName)
        params.add("lastName", existedLastName)
        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("course", courseNumber)

        Mockito.`when`(studentService.findById(UUID.fromString(id))).thenReturn(existentStudent)

        mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}", id).queryParams(params))
                .andExpect(MockMvcResultMatchers.status().isResetContent())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun student_shouldReturnStudentAndOk_WhenStudentExists() {
        val existentStudent = setStudents(pageableDefault).content[0]
        val id = existentStudent.id.toString()

        Mockito.`when`(studentService.findById(UUID.fromString(id))).thenReturn(existentStudent)

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun deleteStudent_shouldReturnNoContent() {
        val id = UUID.randomUUID().toString()

        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    private fun setStudents(pageable: Pageable): Page<StudentDto> {
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
        return PageImpl(studentDtos, pageable, studentDtos.size.toLong())
    }
}