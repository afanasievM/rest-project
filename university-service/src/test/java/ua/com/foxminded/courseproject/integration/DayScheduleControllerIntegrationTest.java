package ua.com.foxminded.courseproject.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;
import ua.com.foxminded.courseproject.service.DayScheduleService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = "classpath:initial_data.sql")
@Transactional
class DayScheduleControllerIntegrationTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DayScheduleService service;


    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 10, 14);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = "e966f608-4621-11ed-b878-0242ac120002";
        DayScheduleDto daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId));
        params.put("startdate", Collections.singletonList(startDate.toString()));

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 10, 14);
        LocalDate endDate = startDate.plusDays(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = "e966f608-4621-11ed-b878-0242ac120002";
        Map<LocalDate, DayScheduleDto> dayScheduleDtoMap = service
                .getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId));
        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + startDate + ".id").
                        value(dayScheduleDtoMap.get(startDate).getId().toString()))
                .andExpect(jsonPath("$." + endDate + ".id").
                        value(dayScheduleDtoMap.get(endDate).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        String teacherId = "e966f608-4621-11ed-b878-0242ac120002";
        DayScheduleDto daySchedule = service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 10, 14);
        String studentId = "f92afb9e-462a-11ed-b878-0242ac120002";
        DayScheduleDto daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("startdate", Collections.singletonList(startDate.toString()));

        mockMvc.perform(get("/students/{id}/schedule", studentId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.of(2022, 10, 14);
        LocalDate endDate = startDate.plusDays(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String studentId = "f92afb9e-462a-11ed-b878-0242ac120002";
        Map<LocalDate, DayScheduleDto> dayScheduleDtoMap = service
                .getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId));
        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        mockMvc.perform(get("/students/{id}/schedule", studentId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + startDate + ".id").
                        value(dayScheduleDtoMap.get(startDate).getId().toString()))
                .andExpect(jsonPath("$." + endDate + ".id").
                        value(dayScheduleDtoMap.get(endDate).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        String studentId = "f92afb9e-462a-11ed-b878-0242ac120002";
        DayScheduleDto daySchedule = service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        mockMvc.perform(get("/students/{id}/schedule", studentId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

}