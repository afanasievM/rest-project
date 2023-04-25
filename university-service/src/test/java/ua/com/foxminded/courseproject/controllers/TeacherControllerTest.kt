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
import ua.com.foxminded.courseproject.dto.TeacherDto
import ua.com.foxminded.courseproject.service.TeacherServiceImpl
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class TeacherControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var teacherService: TeacherServiceImpl
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
    fun teachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() {
        val expected = setTeachers(pageableDefault)

        Mockito.`when`(teacherService.findAll(pageableDefault)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expected.totalElements))
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teachers_shouldReturnTeachersPageAndOk_WhenRequestWithCorrectParameters() {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val expected = setTeachers(pageableDefault)
        params["size"] = listOf(sizeDefault.toString())
        params["page"] = listOf(pageDefault.toString())

        Mockito.`when`(teacherService.findAll(pageableDefault)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers").params(params))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expected.content[0].id.toString()))
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teachers_shouldReturnPageWithAllTeachersAndOk_WhenPageSizeMoreTeachersNumber() {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        val pageNumber = "0"
        val size = "10"
        val pageable: Pageable = PageRequest.of(pageNumber.toInt(), size.toInt())
        val expected = setTeachers(pageable)
        params["size"] = listOf(size)
        params["page"] = listOf(pageNumber)

        Mockito.`when`(teacherService.findAll(pageable)).thenReturn(expected)

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers").params(params))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expected.totalElements))
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

        Mockito.`when`(teacherService.findAll(pageableDefault)).thenReturn(setTeachers(pageableDefault))

        mockMvc.perform(MockMvcRequestBuilders.post("/teachers").params(params))
                .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun updateTeacher_shouldReturnResetContent_WhenTeacherExists() {
        val existentTeacher = setTeachers(pageableDefault).content[0]
        val id = existentTeacher.id.toString()
        val existedFirstName = existentTeacher.firstName
        val existedLastName = existentTeacher.lastName
        val existedBirthDay = existentTeacher.birthDay.minusYears(18)
        val existedFirstDay = existentTeacher.firstDay
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("firstName", existedFirstName)
        params.add("lastName", existedLastName)
        params.add("birthDay", existedBirthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        params.add("firstDay", existedFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))

        Mockito.`when`(teacherService.findById(UUID.fromString(id))).thenReturn(existentTeacher)

        mockMvc.perform(MockMvcRequestBuilders.put("/teachers/{id}", id).params(params))
                .andExpect(MockMvcResultMatchers.status().isResetContent())
    }

    @Throws(Exception::class)
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Test
    fun teacher_shouldReturnTeacherAndOk_WhenTeacherExists() {
        val existentTeacher = setTeachers(pageableDefault).content[0]
        val id = existentTeacher.id.toString()

        Mockito.`when`(teacherService.findById(UUID.fromString(id))).thenReturn(existentTeacher)

        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.status().isOk())
    }

    @Test
    @WithMockUser(username = "admin", authorities = ["ADMIN"])
    @Throws(Exception::class)
    fun deleteTeacher_shouldReturnNoContent() {
        val id = UUID.randomUUID().toString()

        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent())
    }

    private fun setTeachers(pageable: Pageable): Page<TeacherDto> {
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
        return PageImpl(teacherDtos, pageable, teacherDtos.size.toLong())
    }
}