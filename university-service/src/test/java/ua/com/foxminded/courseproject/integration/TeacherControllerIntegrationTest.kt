//package ua.com.foxminded.courseproject.integration
//
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Pageable
//import org.springframework.security.test.context.support.WithMockUser
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.util.MultiValueMap
//import ua.com.foxminded.courseproject.config.DBTestConfig
//import ua.com.foxminded.courseproject.service.TeacherServiceImpl
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.util.*
//
//@SpringBootTest
//@AutoConfigureMockMvc
//internal open class TeacherControllerIntegrationTest : DBTestConfig() {
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Autowired
//    private lateinit var teacherService: TeacherServiceImpl
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() {
//        val expectedSize = 2
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers"))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expectedSize))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teachers_shouldReturnTeachersPageAndOk_WhenRequestWithCorrectParameters() {
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val expectedId = "e966f608-4621-11ed-b878-0242ac120002"
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers").params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expectedId))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teachers_shouldReturnPageWithAllTeachersAndOk_WhenPageSizeMoreTeachersNumber() {
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val expectedElements = "2"
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers").params(params))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expectedElements))
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun createTeacher_shouldReturnCreatedAndCreateTeacher_WhenTeacherNotExists() {
//        val firstName = "test1"
//        val lastName = "test2"
//        val birthDay = LocalDate.now().minusYears(18)
//        val degree = "NTUU KPI"
//        val firstDay = LocalDate.now()
//        val rank = "phd"
//        val salary = 1000
//        val title = "teacher"
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params.add("firstName", firstName)
//        params.add("lastName", lastName)
//        params.add("birthDay", birthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//        params.add("rank", rank)
//        params.add("degree", degree)
//        params.add("firstDay", firstDay.toString())
//        params.add("salary", salary.toString())
//        params.add("title", title)
//        val pageNumber = 0
//        val size = 10
//        val pageable: Pageable = PageRequest.of(pageNumber, size)
//        val expectedSize = 3
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/teachers").params(params))
//            .andExpect(MockMvcResultMatchers.status().isCreated())
//
//        Assertions.assertEquals(expectedSize, teacherService.findAll(pageable).content.size)
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun updateTeacher_shouldReturnResetContentAndUpdateTeacher_WhenTeacherExists() {
//        val existentTeacher = teacherService.findAll(pageableDefault).content[0]
//        val id = existentTeacher.id.toString()
//        val changedFirstname = "CHANGED"
//        val existedLastName = existentTeacher.lastName
//        val existedBirthDay = existentTeacher.birthDay?.minusYears(18)
//        val existedFirstDay = existentTeacher.firstDay
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params.add("firstName", changedFirstname)
//        params.add("lastName", existedLastName)
//        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//        params.add("firstDay", existedFirstDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/teachers/{id}", id).params(params))
//            .andExpect(MockMvcResultMatchers.status().isResetContent())
//
//        Assertions.assertEquals(changedFirstname, teacherService.findById(UUID.fromString(id)).firstName)
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacher_shouldReturnTeacherAndOk_WhenTeacherExists() {
//        val existentTeacher = teacherService.findAll(pageableDefault).content[0]
//        val id = existentTeacher.id.toString()
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}", id))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun deleteTeacher_shouldReturnNoContentAndDelete_WhenTeacherExists() {
//        val existentTeacher = teacherService.findAll(pageableDefault).content[0]
//        val id = existentTeacher.id.toString()
//        val pageNumber = 0
//        val size = 10
//        val pageable: Pageable = PageRequest.of(pageNumber, size)
//        val expectedSize = 1
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/teachers/{id}", id))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isNoContent())
//
//        Assertions.assertEquals(expectedSize, teacherService.findAll(pageable).content.size)
//    }
//}