package ua.com.foxminded.courseproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.foxminded.courseproject.dto.DayScheduleDto;
import ua.com.foxminded.courseproject.dto.LessonDto;
import ua.com.foxminded.courseproject.service.DayScheduleService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DayScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DayScheduleService service;

    Map<LocalDate, DayScheduleDto> dayScheduleDtoMap;

    @BeforeEach
    void setUp() {
        setDaySchedule();
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() throws Exception {
        LocalDate startDate = LocalDate.now();
        DayScheduleDto daySchedule = dayScheduleDtoMap.get(startDate);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));

        when(service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule);

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        when(service.getTeacherDaysSchedule(startDate, endDate, UUID.fromString(teacherId))).thenReturn(dayScheduleDtoMap);

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + startDate + ".id").
                        value(dayScheduleDtoMap.get(startDate).getId().toString()))
                .andExpect(jsonPath("$." + endDate + ".id").
                        value(dayScheduleDtoMap.get(endDate).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacherSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        DayScheduleDto daySchedule = dayScheduleDtoMap.get(startDate);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        when(service.getTeacherOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule);

        mockMvc.perform(get("/teachers/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnStartDayScheduleAndOk_WhenQueryStartDayOnly() throws Exception {
        LocalDate startDate = LocalDate.now();
        DayScheduleDto daySchedule = dayScheduleDtoMap.get(startDate);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String teacherId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));

        when(service.getStudentOneDaySchedule(startDate, UUID.fromString(teacherId))).thenReturn(daySchedule);

        mockMvc.perform(get("/students/{id}/schedule", teacherId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnDaysMapAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(1);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String studentId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        when(service.getStudentDaysSchedule(startDate, endDate, UUID.fromString(studentId))).thenReturn(dayScheduleDtoMap);

        mockMvc.perform(get("/students/{id}/schedule", studentId).params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$." + startDate + ".id").
                        value(dayScheduleDtoMap.get(startDate).getId().toString()))
                .andExpect(jsonPath("$." + endDate + ".id").
                        value(dayScheduleDtoMap.get(endDate).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getStudentSchedule_shouldReturnDayScheduleAndOk_WhenQueryEndDayBeforeStartDay() throws Exception {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.minusDays(1);
        DayScheduleDto daySchedule = dayScheduleDtoMap.get(startDate);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String studentId = UUID.randomUUID().toString();

        params.put("startdate", Collections.singletonList(startDate.toString()));
        params.put("enddate", Collections.singletonList(endDate.toString()));

        when(service.getStudentOneDaySchedule(startDate, UUID.fromString(studentId))).thenReturn(daySchedule);

        mockMvc.perform(get("/students/{id}/schedule", studentId).params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(daySchedule.getId().toString()));
    }

    private void setDaySchedule() {
        dayScheduleDtoMap = new HashMap<>();
        LessonDto lessonOne = new LessonDto();
        lessonOne.setId(UUID.randomUUID());
        LessonDto lessonTwo = new LessonDto();
        lessonTwo.setId(UUID.randomUUID());
        DayScheduleDto firstDay = new DayScheduleDto();
        firstDay.setId(UUID.randomUUID());
        firstDay.setDayNumber(1);
        firstDay.setLessons(Arrays.asList(lessonOne, lessonTwo));
        DayScheduleDto secondDay = new DayScheduleDto();
        secondDay.setId(UUID.randomUUID());
        secondDay.setDayNumber(2);
        secondDay.setLessons(Arrays.asList(lessonOne));
        dayScheduleDtoMap.put(LocalDate.now(), firstDay);
        dayScheduleDtoMap.put(LocalDate.now().plusDays(1), secondDay);
    }
}