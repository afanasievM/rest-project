//package ua.com.foxminded.courseproject.integration
//
//import org.junit.jupiter.api.Test
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.security.test.context.support.WithMockUser
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers
//import org.springframework.util.LinkedMultiValueMap
//import org.springframework.util.MultiValueMap
//import ua.com.foxminded.courseproject.config.DBTestConfig
//import ua.com.foxminded.courseproject.service.DayScheduleService
//import java.time.LocalDate
//import java.util.*
//
//@SpringBootTest
//@AutoConfigureMockMvc
//internal open class DayScheduleControllerIntegrationTest : DBTestConfig() {
//    @Autowired
//    private lateinit var mockMvc: MockMvc
//
//    @Autowired
//    private lateinit var service: DayScheduleService
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
//        val startDate = LocalDate.of(2022, 10, 14)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
//        val daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))
//        params["startdate"] = listOf(startDate.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.of(2022, 10, 14)
//        val endDate = startDate.plusDays(1)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
//        val dayScheduleDtoMap = service
//            .getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId))
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(
//                MockMvcResultMatchers.jsonPath("$.$startDate.id").value(dayScheduleDtoMap[startDate]?.id.toString())
//            )
//            .andExpect(MockMvcResultMatchers.jsonPath("$.$endDate.id").value(dayScheduleDtoMap[endDate]?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun teacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.minusDays(1)
//        val teacherId = "e966f608-4621-11ed-b878-0242ac120002"
//        val daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/teachers/{id}/schedule", teacherId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() {
//        val startDate = LocalDate.of(2022, 10, 14)
//        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
//        val daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId))
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params["startdate"] = listOf(startDate.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", studentId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.of(2022, 10, 14)
//        val endDate = startDate.plusDays(1)
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
//        val dayScheduleDtoMap = service
//            .getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId))
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", studentId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(
//                MockMvcResultMatchers.jsonPath("$.$startDate.id").value(dayScheduleDtoMap[startDate]?.id.toString())
//            )
//            .andExpect(MockMvcResultMatchers.jsonPath("$.$endDate.id").value(dayScheduleDtoMap[endDate]?.id.toString()))
//    }
//
//    @Throws(Exception::class)
//    @WithMockUser(username = "admin", authorities = ["ADMIN"])
//    @Test
//    fun studentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() {
//        val startDate = LocalDate.now()
//        val endDate = startDate.minusDays(1)
//        val studentId = "f92afb9e-462a-11ed-b878-0242ac120002"
//        val daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId))
//        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
//        params["startdate"] = listOf(startDate.toString())
//        params["enddate"] = listOf(endDate.toString())
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}/schedule", studentId).params(params))
//            .andExpect(MockMvcResultMatchers.status().isOk())
//            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(daySchedule?.id.toString()))
//    }
//}