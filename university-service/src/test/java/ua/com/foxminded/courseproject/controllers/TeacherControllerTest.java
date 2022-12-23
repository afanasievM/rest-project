package ua.com.foxminded.courseproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ua.com.foxminded.courseproject.dto.TeacherDto;
import ua.com.foxminded.courseproject.service.TeacherServiceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherServiceImpl teacherService;

    private Pageable pageableDefault;

    private Integer pageDefault;

    private Integer sizeDefault;


    @BeforeEach
    void setUp() {
        this.pageDefault = 0;
        this.sizeDefault = 5;
        this.pageableDefault = PageRequest.of(pageDefault, sizeDefault);
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeachers_shouldReturnAllTeachersAndOk_WhenRequestWithoutParameters() throws Exception {
        Page<TeacherDto> expected = setTeachers(pageableDefault);

        when(teacherService.findAll(pageableDefault)).thenReturn(expected);

        mockMvc.perform(get("/teachers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeachers_shouldReturnTeachersPageAndOk_WhenRequestWithCorrectParameters() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        Page<TeacherDto> expected = setTeachers(pageableDefault);
        params.put("size", Collections.singletonList(String.valueOf(sizeDefault)));
        params.put("page", Collections.singletonList(String.valueOf(pageDefault)));

        when(teacherService.findAll(pageableDefault)).thenReturn(expected);

        mockMvc.perform(get("/teachers").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageDefault))
                .andExpect(jsonPath("$.pageable.pageSize").value(sizeDefault))
                .andExpect(jsonPath("$.content[0].id").value(expected.getContent().get(0).getId().toString()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeachers_shouldReturnPageWithAllTeachersAndOk_WhenPageSizeMoreTeachersNumber() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String pageNumber = "0";
        String size = "10";
        Pageable pageable = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(size));
        Page<TeacherDto> expected = setTeachers(pageable);
        params.put("size", Collections.singletonList(size));
        params.put("page", Collections.singletonList(pageNumber));

        when(teacherService.findAll(pageable)).thenReturn(expected);

        mockMvc.perform(get("/teachers").params(params))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber").value(pageNumber))
                .andExpect(jsonPath("$.pageable.pageSize").value(size))
                .andExpect(jsonPath("$.totalElements").value(expected.getTotalElements()));
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void createTeacher_shouldReturnCreated_WhenTeacherNotExists() throws Exception {
        String firstName = "test1";
        String lastName = "test2";
        LocalDate birthDay = LocalDate.now().minusYears(18);
        String degree = "NTUU KPI";
        LocalDate firstDay = LocalDate.now();
        String rank = "phd";
        Integer salary = 1000;
        String title = "teacher";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", firstName);
        params.add("lastName", lastName);
        params.add("birthDay", birthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("rank", rank);
        params.add("degree", degree);
        params.add("firstDay", firstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("salary", String.valueOf(salary));
        params.add("title", title);

        when(teacherService.findAll(pageableDefault)).thenReturn(setTeachers(pageableDefault));

        mockMvc.perform(post("/teachers").params(params))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void updateTeacher_shouldReturnResetContent_WhenTeacherExists() throws Exception {
        TeacherDto existentTeacher = setTeachers(pageableDefault).getContent().get(0);
        String id = existentTeacher.getId().toString();
        String existedFirstName = existentTeacher.getFirstName();
        String existedLastName = existentTeacher.getLastName();
        LocalDate existedBirthDay = existentTeacher.getBirthDay().minusYears(18);
        LocalDate existedFirstDay = existentTeacher.getFirstDay();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", existedFirstName);
        params.add("lastName", existedLastName);
        params.add("birthDay", existedBirthDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        params.add("firstDay", existedFirstDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        when(teacherService.findById(UUID.fromString(id))).thenReturn(existentTeacher);

        mockMvc.perform(put("/teachers/{id}", id).params(params))
                .andExpect(status().isResetContent());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getTeacher_shouldReturnTeacherAndOk_WhenTeacherExists() throws Exception {
        TeacherDto existentTeacher = setTeachers(pageableDefault).getContent().get(0);
        String id = existentTeacher.getId().toString();

        when(teacherService.findById(UUID.fromString(id))).thenReturn(existentTeacher);

        mockMvc.perform(get("/teachers/{id}", id))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void deleteTeacher_shouldReturnNoContent() throws Exception {
        String id = UUID.randomUUID().toString();

        mockMvc.perform(delete("/teachers/{id}", id))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private Page<TeacherDto> setTeachers(Pageable pageable) {
        List<TeacherDto> teacherDtos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TeacherDto teacher = new TeacherDto();
            teacher.setId(UUID.randomUUID());
            teacher.setFirstName("firstName" + i);
            teacher.setLastName("lastName" + i);
            teacher.setBirthDay(LocalDate.now());
            teacher.setRank("phd");
            teacher.setSalary(i);
            teacher.setTitle("teacher");
            teacher.setFirstDay(LocalDate.now().minusDays(1000));
            teacher.setDegree("NTUU KPI");
            teacherDtos.add(teacher);
        }
        return new PageImpl<>(teacherDtos, pageable, teacherDtos.size());
    }
}