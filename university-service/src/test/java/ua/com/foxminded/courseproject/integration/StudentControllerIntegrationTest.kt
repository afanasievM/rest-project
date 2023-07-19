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
//import ua.com.foxminded.courseproject.service.StudentServiceImpl
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//import java.util.*
//
//@SpringBootTest
//@AutoConfigureMockMvc
//internal open class StudentControllerIntegrationTest : DBTestConfig() {
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Autowired
//    private lateinit var studentService: StudentServiceImpl
//    private lateinit var pageableDefault: Pageable
//    private var pageDefault: Int = 0
//    private var sizeDefault: Int = 0
//
//    @BeforeEach
//    fun setUpDefaults() {
//        pageDefault = 0
//        sizeDefault = 1
//        pageableDefault = PageRequest.of(pageDefault, sizeDefault)
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun students_shouldReturnAllStudentsAndOk_WhenRequestWithoutParameters() {
//        val expectedSize = 2
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expectedSize))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun students_shouldReturnStudentsPageAndOk_WhenRequestWithCorrectParameters() {
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val expectedId = "f92afb9e-462a-11ed-b878-0242ac120002"
//        params["size"] = listOf(sizeDefault.toString())
//        params["page"] = listOf(pageDefault.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students").params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageDefault))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(sizeDefault))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(expectedId))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun students_shouldReturnPageWithAllStudentsAndOk_WhenPageSizeMoreStudentsNumber() {
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val pageNumber = "0"
//        val size = "10"
//        val expectedElements = "2"
//        params["size"] = listOf(size)
//        params["page"] = listOf(pageNumber)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students").params(params))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageNumber))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(size))
//            .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(expectedElements))
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun createStudent_shouldReturnCreatedAndCreateStudent_WhenStudentNotExists() {
//        val firstName = "firstName"
//        val lastName = "LastName"
//        val birthDay = LocalDate.now().minusYears(30)
//        val courseNumber = "1"
//        val captain = false
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params.add("firstName", firstName)
//        params.add("lastName", lastName)
//        params.add("birthDay", birthDay.toString())
//        params.add("course", courseNumber)
//        params.add("captain", captain.toString())
//        val pageNumber = 0
//        val size = 10
//        val pageable: Pageable = PageRequest.of(pageNumber, size)
//        val expectedSize = 3
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/students").queryParams(params))
//            .andExpect(MockMvcResultMatchers.status().isCreated())
//
//        Assertions.assertEquals(expectedSize, studentService.findAll(pageable).content.size)
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun updateStudent_shouldReturnResetContentAndUpdateStudent_WhenStudentExists() {
//        val existentStudent = studentService.findAll(pageableDefault).content[0]
//        val id = existentStudent.id.toString()
//        val changedFirstname = "CHANGED"
//        val existedLastName = existentStudent.lastName
//        val courseNumber = "1"
//        val existedBirthDay = existentStudent.birthDay?.minusYears(18)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params.add("firstName", changedFirstname)
//        params.add("lastName", existedLastName)
//        params.add("birthDay", existedBirthDay?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//        params.add("course", courseNumber)
//
//        mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}", id).params(params))
//            .andExpect(MockMvcResultMatchers.status().isResetContent())
//
//        Assertions.assertEquals(changedFirstname, studentService.findById(UUID.fromString(id)).firstName)
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun student_shouldReturnStudentAndOk_WhenStudentExists() {
//        val existentStudent = studentService.findAll(pageableDefault).content[0]
//        val id = existentStudent.id.toString()
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", id))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//    }
//
//    @Test
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Throws(Exception::class)
//    fun deleteStudent_shouldReturnNoContentAndDelete_WhenStudentExists() {
//        val existentStudent = studentService.findAll(pageableDefault).content[0]
//        val id = existentStudent.id.toString()
//        val pageNumber = 0
//        val size = 10
//        val pageable: Pageable = PageRequest.of(pageNumber, size)
//        val expectedSize = 1
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", id))
//            .andDo(MockMvcResultHandlers.print())
//            .andExpect(MockMvcResultMatchers.status().isNoContent())
//
//        Assertions.assertEquals(expectedSize, studentService.findAll(pageable).content.size)
//    }
//}