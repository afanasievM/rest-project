//package ua.com.foxminded.courseproject.controllers
//
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.mockito.Mockito
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.security.test.context.support.WithMockUser
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.util.MultiValueMap
//import ua.com.foxminded.courseproject.dto.DayScheduleDto
//import ua.com.foxminded.courseproject.dto.LessonDto
//import ua.com.foxminded.courseproject.service.DayScheduleService
//import java.time.LocalDate
//import java.util.*
//
//@SpringBootTest
//@AutoConfigureMockMvc
//internal class DayScheduleControllerTest {
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @MockBean
//    private lateinit var service: DayScheduleService
//    private lateinit var dayScheduleDtoMap: MutableMap<LocalDate, DayScheduleDto>
//
//    @BeforeEach
//    fun setUp() {
//        setDaySchedule()
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
//        val startDate = LocalDate.now()
//        val daySchedule = dayScheduleDtoMap[startDate]
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//
//        Mockito.`when`(service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.plusDays(1)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        Mockito.`when`(service.getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId))).thenReturn(dayScheduleDtoMap)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.$startDate.id").value(dayScheduleDtoMap[startDate]?.id.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.$endDate.id").value(dayScheduleDtoMap[endDate]?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.minusDays(1)
//        val daySchedule = dayScheduleDtoMap[startDate]
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        Mockito.`when`(service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
//        val startDate = LocalDate.now()
//        val daySchedule = dayScheduleDtoMap[startDate]
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//
//        Mockito.`when`(service.getStudentOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", teacherId).params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.plusDays(1)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val studentId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        Mockito.`when`(service.getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId))).thenReturn(dayScheduleDtoMap)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", studentId).params(params))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.$startDate.id").value(dayScheduleDtoMap[startDate]?.id.toString()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.$endDate.id").value(dayScheduleDtoMap[endDate]?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.minusDays(1)
//        val daySchedule = dayScheduleDtoMap[startDate]
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val studentId = UUID.randomUUID().toString()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        Mockito.`when`(service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId))).thenReturn(daySchedule)
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", studentId).params(params))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    private fun setDaySchedule() {
//        dayScheduleDtoMap = HashMap()
//        val lessonOne = LessonDto()
//        lessonOne.id = UUID.randomUUID()
//        val lessonTwo = LessonDto()
//        lessonTwo.id = UUID.randomUUID()
//        val firstDay = DayScheduleDto()
//        firstDay.id = UUID.randomUUID()
//        firstDay.dayNumber = 1
//        firstDay.lessons = Arrays.asList(lessonOne, lessonTwo)
//        val secondDay = DayScheduleDto()
//        secondDay.id = UUID.randomUUID()
//        secondDay.dayNumber = 2
//        secondDay.lessons = Arrays.asList(lessonOne)
//        dayScheduleDtoMap[LocalDate.now()] = firstDay
//        dayScheduleDtoMap[LocalDate.now().plusDays(1)] = secondDay
//    }
//}